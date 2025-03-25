package com.fuelcalculator.interfaces.cli;

import com.fuelcalculator.adapters.controllers.FuelCalculationController;
import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculatonPresenter;
import com.fuelcalculator.domain.services.FuelCalculationService;
import com.fuelcalculator.infraestructure.gatewayimp.FueCalculationGatewayImpl;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;

import java.util.Scanner;

public class FuelCalculatorCLI {

    public static void main(String[] args) {
        FuelCalculationController fuelCalculationController = getFuelCalculationController();


        // Objeto Scanner para obter os dados do usuário
        Scanner scanner = new Scanner(System.in);


        System.out.println("Calculadora de Consumo de combustível");
        System.out.print("Digite a quilometragem inicial: ");
        double initialKilometers = scanner.nextDouble();
        System.out.print("Digite a quantidade de litros abastecidos");
        double litersFilled = scanner.nextDouble();
        System.out.print("Digite a quilometragem final: ");
        double finalKilometers = scanner.nextDouble();
        System.out.print("Digite o valor gasto para abastecer: ");
        double totalCost = scanner.nextDouble();


        fuelCalculationController.handleCalculationRequest(initialKilometers, litersFilled, finalKilometers, totalCost);

        scanner.close();

    }

    private static FuelCalculationController getFuelCalculationController() {
        FuelCalculationService fuelCalculationService = new FuelCalculationService();
        CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase = new CalculateFuelConsumptionUseCase(fuelCalculationService);
        FuelCalculatonPresenter fuelCalculationPresenter = new FuelCalculatonPresenter();
        FuelCalculationGateway fuelCalculationGateway = new FueCalculationGatewayImpl();
        return new FuelCalculationController(calculateFuelConsumptionUseCase,
                fuelCalculationPresenter, fuelCalculationGateway);
    }
}
