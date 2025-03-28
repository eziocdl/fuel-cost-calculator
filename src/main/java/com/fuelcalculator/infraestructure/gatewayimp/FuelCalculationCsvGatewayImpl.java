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

    private final String filePath;
    private final String idFilePath;
    private long nextId = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private List<FuelCalculationOutput> calculations = new ArrayList<>();

    public FuelCalculationCsvGatewayImpl(String filePath) {
        this("fuel_calculations.csv", "fuel_calculations_id.txt");
    }

    public FuelCalculationCsvGatewayImpl(String filePath, String idFilePath) {
        this.filePath = filePath;
        this.idFilePath = idFilePath;
        createResourceFiles();
        nextId = readLastId() + 1;
        calculations = readCalculationsFromFile();
    }

    private void createResourceFiles() {
        try {
            Path resourcesPath = Paths.get("src", "main", "resources");
            if (!Files.exists(resourcesPath)) {
                Files.createDirectories(resourcesPath);
            }
            Path calculationsFilePath = resourcesPath.resolve(filePath);
            Path idFilePath = resourcesPath.resolve(this.idFilePath);
            if (!Files.exists(calculationsFilePath)) {
                Files.createFile(calculationsFilePath);
            }
            if (!Files.exists(idFilePath)) {
                Files.createFile(idFilePath);
            }
        } catch (IOException e) {
            // Removido o log de depuração: System.err.println("Erro ao criar arquivos de recursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private long readLastId() {
        try {
            Path idPath = Paths.get("src", "main", "resources", idFilePath);
            if (Files.exists(idPath) && Files.size(idPath) > 0) {
                List<String> lines = Files.readAllLines(idPath);
                return lines.isEmpty() ? 0 : Long.parseLong(lines.get(0));
            }
        } catch (Exception e) {
            // Removido o log de depuração: System.err.println("Erro ao ler último ID: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private void writeLastId(long lastId) {
        try {
            Path idPath = Paths.get("src", "main", "resources", idFilePath);
            Files.writeString(idPath, String.valueOf(lastId));
        } catch (Exception e) {
            // Removido o log de depuração: System.err.println("Erro ao gravar último ID: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeCalculationsToFile(List<FuelCalculationOutput> calculations) {
        File file = Paths.get("src/main/resources", filePath).toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (FuelCalculationOutput calculation : calculations) {
                String line = calculation.getId() + "," +
                        calculation.getConsumption() + "," +
                        calculation.getCostPerKilometer() + "," +
                        calculation.getCalculationDate().format(formatter);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Removido o log de depuração: System.err.println("Erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    private List<FuelCalculationOutput> readCalculationsFromFile() {
        List<FuelCalculationOutput> calculations = new ArrayList<>();
        try {
            Path path = Paths.get("src", "main", "resources", filePath);
            if (Files.exists(path) && Files.size(path) > 0) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] values = line.split(",");
                    if (values.length == 4) {
                        long id = Long.parseLong(values[0]);
                        double consumption = Double.parseDouble(values[1]);
                        double costPerKilometer = Double.parseDouble(values[2]);
                        LocalDate calculationDate = LocalDate.parse(values[3], formatter);
                        FuelCalculationOutput calculation = new FuelCalculationOutput(id, consumption, costPerKilometer, calculationDate);
                        calculations.add(calculation);
                    }
                }
            }
        } catch (Exception e) {
            // Removido o log de depuração: System.err.println("Erro ao ler cálculos do arquivo: " + e.getMessage());
            e.printStackTrace();
        }
        return calculations;
    }

    @Override
    public Optional<FuelCalculationOutput> saveCalculation(FuelCalculationOutput output) {
        FuelCalculationOutput outputWithId = new FuelCalculationOutput(nextId++, output.getConsumption(), output.getCostPerKilometer(), output.getCalculationDate());
        calculations.add(outputWithId);
        writeCalculationsToFile(calculations);
        writeLastId(nextId - 1);
        return Optional.of(outputWithId);
    }

    @Override
    public List<FuelCalculationOutput> getCalculations() {
        return calculations;
    }

    @Override
    public List<FuelCalculationOutput> getCalculationByPeriod(int month, int year) {
        List<FuelCalculationOutput> filteredCalculations = new ArrayList<>();
        for (FuelCalculationOutput calculation : calculations) {
            if (calculation.getCalculationDate().getMonthValue() == month &&
                    calculation.getCalculationDate().getYear() == year) {
                filteredCalculations.add(calculation);
            }
        }
        return filteredCalculations;
    }

    @Override
    public Optional<FuelCalculationOutput> updateCalculation(FuelCalculationOutput output) {
        for (int i = 0; i < calculations.size(); i++) {
            if (calculations.get(i).getId().equals(output.getId())) {
                calculations.set(i, output);
                writeCalculationsToFile(calculations);
                return Optional.of(output);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCalculation(FuelCalculationOutput output) {
        for (int i = 0; i < calculations.size(); i++) {
            if (calculations.get(i).getId().equals(output.getId())) {
                calculations.remove(i);
                writeCalculationsToFile(calculations);
                return true;
            }
        }
        return false;
    }
}