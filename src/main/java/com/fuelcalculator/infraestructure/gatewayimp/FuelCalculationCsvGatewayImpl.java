package com.fuelcalculator.infraestructure.gatewayimp;

import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuelCalculationCsvGatewayImpl implements FuelCalculationGateway {

    private final String filePath = "src/main/resources/fuel_calculations.csv";
    private long nextId = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public FuelCalculationCsvGatewayImpl() {
        // Garantir que o diretório exista
        Path directory = Paths.get("src/main/resources");
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<FuelCalculationOutput> saveCalculation(FuelCalculationOutput output) {
        FuelCalculationOutput outputWithId = new FuelCalculationOutput(nextId++, output.getConsumption(), output.getCostPerKilometer(), output.getCalculationDate());
        writeCalculationToFile(outputWithId);
        return Optional.of(outputWithId);
    }

    @Override
    public List<FuelCalculationOutput> getCalculations() {
        return readCalculationsFromFile();
    }

    @Override
    public List<FuelCalculationOutput> getCalculationByPeriod(int month, int year) {
        List<FuelCalculationOutput> allCalculations = readCalculationsFromFile();
        List<FuelCalculationOutput> filteredCalculations = new ArrayList<>();
        for (FuelCalculationOutput calculation : allCalculations) {
            if (calculation.getCalculationDate().getMonthValue() == month && calculation.getCalculationDate().getYear() == year) {
                filteredCalculations.add(calculation);
            }
        }
        return filteredCalculations;
    }

    @Override
    public Optional<FuelCalculationOutput> updateCalculation(FuelCalculationOutput output) {
        List<FuelCalculationOutput> calculations = readCalculationsFromFile();
        List<FuelCalculationOutput> updatedCalculations = new ArrayList<>();
        boolean updated = false;
        for (FuelCalculationOutput calculation : calculations) {
            if (calculation.getId().equals(output.getId())) {
                updatedCalculations.add(output);
                updated = true;
            } else {
                updatedCalculations.add(calculation);
            }
        }
        if (updated) {
            writeCalculationsToFile(updatedCalculations);
            return Optional.of(output);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCalculation(FuelCalculationOutput output) {
        List<FuelCalculationOutput> calculations = readCalculationsFromFile();
        List<FuelCalculationOutput> updatedCalculations = new ArrayList<>();
        boolean deleted = false;
        for (FuelCalculationOutput calculation : calculations) {
            if (calculation.getId().equals(output.getId())) {
                deleted = true;
            } else {
                updatedCalculations.add(calculation);
            }
        }
        if (deleted) {
            writeCalculationsToFile(updatedCalculations);
            return true;
        } else {
            return false;
        }
    }

    private void writeCalculationToFile(FuelCalculationOutput calculation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = calculation.getId() + "," + calculation.getConsumption() + "," + calculation.getCostPerKilometer() + "," + calculation.getCalculationDate().format(formatter);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCalculationsToFile(List<FuelCalculationOutput> calculations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (FuelCalculationOutput calculation : calculations) {
                writeCalculationToFile(calculation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<FuelCalculationOutput> readCalculationsFromFile() {
        List<FuelCalculationOutput> calculations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                long id = Long.parseLong(values[0]);
                double consumption = Double.parseDouble(values[1]);
                double costPerKilometer = Double.parseDouble(values[2]);
                LocalDate calculationDate = LocalDate.parse(values[3], formatter);
                calculations.add(new FuelCalculationOutput(id, consumption, costPerKilometer, calculationDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calculations;
    }
}