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

/**
 * Implementação do gateway para armazenar e recuperar dados de cálculos de combustível em um arquivo CSV.
 * Este gateway lida com a persistência de dados, leitura e escrita de cálculos em um arquivo CSV,
 * além de gerenciar o ID sequencial dos cálculos.
 */
public class FuelCalculationCsvGatewayImpl implements FuelCalculationGateway {

    private final String filePath;
    private final String idFilePath;
    private long nextId = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private List<FuelCalculationOutput> calculations = new ArrayList<>();

    /**
     * Construtor padrão que inicializa o gateway com nomes de arquivos padrão.
     * @param filePath O caminho do arquivo CSV para armazenar os cálculos.
     */
    public FuelCalculationCsvGatewayImpl(String filePath) {
        this("fuel_calculations.csv", "fuel_calculations_id.txt");
    }

    /**
     * Construtor que permite especificar os caminhos dos arquivos CSV e de ID.
     * @param filePath O caminho do arquivo CSV para armazenar os cálculos.
     * @param idFilePath O caminho do arquivo para armazenar o último ID utilizado.
     */
    public FuelCalculationCsvGatewayImpl(String filePath, String idFilePath) {
        this.filePath = filePath;
        this.idFilePath = idFilePath;
        createResourceFiles();
        nextId = readLastId() + 1;
        calculations = readCalculationsFromFile();
    }

    /**
     * Cria os arquivos CSV e de ID se eles não existirem.
     * Em caso de erro, imprime a pilha de exceção.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Lê o último ID utilizado do arquivo de ID.
     * @return O último ID lido, ou 0 se o arquivo não existir ou estiver vazio.
     */
    private long readLastId() {
        try {
            Path idPath = Paths.get("src", "main", "resources", idFilePath);
            if (Files.exists(idPath) && Files.size(idPath) > 0) {
                List<String> lines = Files.readAllLines(idPath);
                return lines.isEmpty() ? 0 : Long.parseLong(lines.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Escreve o último ID utilizado no arquivo de ID.
     * @param lastId O último ID a ser escrito.
     */
    private void writeLastId(long lastId) {
        try {
            Path idPath = Paths.get("src", "main", "resources", idFilePath);
            Files.writeString(idPath, String.valueOf(lastId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve a lista de cálculos no arquivo CSV.
     * @param calculations A lista de cálculos a serem escritos.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Lê os cálculos do arquivo CSV e retorna uma lista de objetos FuelCalculationOutput.
     * @return A lista de cálculos lidos do arquivo.
     */
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
            e.printStackTrace();
        }
        return calculations;
    }

    /**
     * Salva um novo cálculo no arquivo CSV e retorna um Optional contendo o cálculo salvo.
     * @param output O objeto FuelCalculationOutput a ser salvo.
     * @return Um Optional contendo o objeto FuelCalculationOutput salvo.
     */
    @Override
    public Optional<FuelCalculationOutput> saveCalculation(FuelCalculationOutput output) {
        FuelCalculationOutput outputWithId = new FuelCalculationOutput(nextId++, output.getConsumption(), output.getCostPerKilometer(), output.getCalculationDate());
        calculations.add(outputWithId);
        writeCalculationsToFile(calculations);
        writeLastId(nextId - 1);
        return Optional.of(outputWithId);
    }

    /**
     * Retorna a lista de todos os cálculos armazenados.
     * @return A lista de cálculos armazenados.
     */
    @Override
    public List<FuelCalculationOutput> getCalculations() {
        return calculations;
    }

    /**
     * Retorna a lista de cálculos filtrados por mês e ano.
     * @param month O mês para filtrar os cálculos.
     * @param year O ano para filtrar os cálculos.
     * @return A lista de cálculos filtrados.
     */
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

    /**
     * Atualiza um cálculo existente e retorna um Optional contendo o cálculo atualizado.
     * @param output O objeto FuelCalculationOutput com os dados atualizados.
     * @return Um Optional contendo o objeto FuelCalculationOutput atualizado, ou Optional.empty() se o cálculo não for encontrado.
     */
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

    /**
     * Deleta um cálculo existente e retorna true se a operação for bem-sucedida.
     * @param output O objeto FuelCalculationOutput a ser deletado.
     * @return true se o cálculo for deletado com sucesso, false caso contrário.
     */
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