package com.fuelcalculator.interfaces.cli;

import com.fuelcalculator.adapters.controllers.FuelCalculationController;
import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculationPresenter;
import com.fuelcalculator.domain.services.FuelCalculationService;
import com.fuelcalculator.infraestructure.gatewayimp.FuelCalculationCsvGatewayImpl;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;
import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interface de linha de comando (CLI) para a calculadora de consumo de combustível.
 * Esta classe gerencia a interação com o usuário, processando entradas e exibindo resultados.
 */
public class FuelCalculatorCLI {

    /**
     * Cria e retorna um controlador de cálculo de combustível.
     * @return Uma instância de FuelCalculationController configurada.
     */
    private static FuelCalculationController getFuelCalculationController() {
        FuelCalculationService fuelCalculationService = new FuelCalculationService();
        CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase = new CalculateFuelConsumptionUseCase(fuelCalculationService);
        FuelCalculationPresenter fuelCalculationPresenter = new FuelCalculationPresenter();
        String filePath = "fuel_calculations.csv";
        FuelCalculationGateway fuelCalculationGateway = new FuelCalculationCsvGatewayImpl(filePath);
        return new FuelCalculationController(fuelCalculationGateway, calculateFuelConsumptionUseCase, fuelCalculationPresenter, fuelCalculationGateway);
    }

    /**
     * Método principal que inicia a aplicação CLI.
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        FuelCalculationController fuelCalculationController = getFuelCalculationController();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nCalculadora de Consumo de Combustível");
            System.out.println("1. Criar Cálculo");
            System.out.println("2. Listar Cálculos");
            System.out.println("3. Atualizar Cálculo");
            System.out.println("4. Deletar Cálculo");
            System.out.println("5. Relatório por Período");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createCalculation(scanner, fuelCalculationController);
                    break;
                case 2:
                    listCalculations(fuelCalculationController);
                    break;
                case 3:
                    updateCalculation(scanner, fuelCalculationController);
                    break;
                case 4:
                    deleteCalculation(scanner, fuelCalculationController);
                    break;
                case 5:
                    reportByPeriod(scanner, fuelCalculationController);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (choice != 0);

        scanner.close();
    }

    /**
     * Cria um novo cálculo de consumo de combustível com base na entrada do usuário.
     * @param scanner O scanner para ler a entrada do usuário.
     * @param controller O controlador para processar a solicitação de cálculo.
     */
    private static void createCalculation(Scanner scanner, FuelCalculationController controller) {
        System.out.print("Quilometragem Inicial: ");
        double initialKilometers = scanner.nextDouble();

        System.out.print("Litros Abastecidos: ");
        double litersFilled = scanner.nextDouble();

        System.out.print("Quilometragem Final: ");
        double finalKilometers = scanner.nextDouble();

        System.out.print("Custo Total: R$ ");
        double totalCost = scanner.nextDouble();

        controller.handleCalculationRequest(initialKilometers, litersFilled, finalKilometers, totalCost);
    }

    /**
     * Lista todos os cálculos de consumo de combustível armazenados.
     * @param controller O controlador para recuperar a lista de cálculos.
     */
    private static void listCalculations(FuelCalculationController controller) {
        List<FuelCalculationOutput> calculations = controller.getCalculations();
        if (calculations.isEmpty()) {
            System.out.println("Nenhum cálculo de combustível encontrado.");
        } else {
            System.out.println("Lista de Cálculos de Combustível:");
            System.out.println("----------------------------------");
            for (FuelCalculationOutput calculation : calculations) {
                System.out.println("ID: " + calculation.getId());
                System.out.println("Consumo: R$ " + calculation.getConsumption() + " km/l");
                System.out.println("Custo por km: R$ " + calculation.getCostPerKilometer());
                System.out.println("Data: " + calculation.getCalculationDate());
                System.out.println("----------------------------------");
            }
        }
    }

    /**
     * Atualiza um cálculo de consumo de combustível existente com base na entrada do usuário.
     * @param scanner O scanner para ler a entrada do usuário.
     * @param controller O controlador para processar a atualização do cálculo.
     */
    private static void updateCalculation(Scanner scanner, FuelCalculationController controller) {
        System.out.print("ID do Cálculo a Atualizar: ");
        long id = scanner.nextLong();
        System.out.print("Nova Quilometragem Inicial: ");
        double initialKilometers = scanner.nextDouble();
        System.out.print("Novos Litros Abastecidos: ");
        double litersFilled = scanner.nextDouble();
        System.out.print("Nova Quilometragem Final: ");
        double finalKilometers = scanner.nextDouble();
        System.out.print("Novo Custo Total: ");
        double totalCost = scanner.nextDouble();

        double consumption = litersFilled / (finalKilometers - initialKilometers);
        double costPerKilometer = totalCost / (finalKilometers - initialKilometers);

        FuelCalculationOutput updatedCalculation = new FuelCalculationOutput(id, consumption, costPerKilometer, LocalDate.now());
        Optional<FuelCalculationOutput> result = controller.updateCalculation(updatedCalculation);
        result.ifPresent(System.out::println);
    }

    /**
     * Deleta um cálculo de consumo de combustível existente com base no ID fornecido pelo usuário.
     * @param scanner O scanner para ler a entrada do usuário.
     * @param controller O controlador para processar a exclusão do cálculo.
     */
    private static void deleteCalculation(Scanner scanner, FuelCalculationController controller) {
        System.out.print("ID do Cálculo a Deletar: ");
        long id = scanner.nextLong();
        FuelCalculationOutput calculationToDelete = new FuelCalculationOutput(id, 0, 0, LocalDate.now());
        boolean deleted = controller.deleteCalculation(calculationToDelete);
        System.out.println(deleted ? "Cálculo deletado com sucesso!" : "Falha ao deletar cálculo.");
    }

    /**
     * Gera um relatório de cálculos de consumo de combustível para um período especificado pelo usuário.
     * @param scanner O scanner para ler a entrada do usuário.
     * @param controller O controlador para recuperar os cálculos por período.
     */
    private static void reportByPeriod(Scanner scanner, FuelCalculationController controller) {
        System.out.print("Mês: ");
        int month = scanner.nextInt();
        System.out.print("Ano: ");
        int year = scanner.nextInt();

        List<FuelCalculationOutput> calculations = controller.getCalculationByPeriod(month, year);
        if (calculations.isEmpty()) {
            System.out.println("Nenhum cálculo encontrado para o período informado.");
        } else {
            System.out.println("Relatório de Cálculos de Combustível:");
            System.out.println("----------------------------------");
            for (FuelCalculationOutput calculation : calculations) {
                System.out.println("ID: " + calculation.getId());
                System.out.println("Consumo: " + calculation.getConsumption() + " litros/km");
                System.out.println("Custo por km: R$ " + calculation.getCostPerKilometer());
                System.out.println("Data: " + calculation.getCalculationDate());
                System.out.println("----------------------------------");
            }
        }
    }
}