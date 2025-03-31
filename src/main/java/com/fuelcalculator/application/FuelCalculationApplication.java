package com.fuelcalculator.application;

import com.fuelcalculator.adapters.controllers.FuelCalculationController;
import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculationPresenter;
import com.fuelcalculator.domain.services.FuelCalculationService;
import com.fuelcalculator.infraestructure.gatewayimp.FuelCalculationCsvGatewayImpl;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;

/**
 * Classe principal do projeto, responsável por iniciar a aplicação e executar o fluxo de cálculo de combustível.
 */

public class FuelCalculationApplication {

    /**
     * Método principal da aplicação.
     *
     * @param args Argumentos da linha de comando.
     */

    public static void main(String[] args) {


        // Simulação de entrada do usuário

        double initialKilometers = 1000;
        double litersFilled = 50;
        double finalKilometers = 1500;
        double totalCost = 250;

        // Instâncias necessárias
        FuelCalculationService fuelCalculationService = new FuelCalculationService();
        FuelCalculationController fuelCalculationController = getFuelCalculationController(fuelCalculationService);

        // Executa o cálculo

        fuelCalculationController.handleCalculationRequest(initialKilometers, litersFilled, finalKilometers, totalCost);

    }

    /**
     * Retorna uma instância do FuelCalculationController.
     *
     * @param fuelCalculationService Serviço de cálculo de combustível.
     * @return Instância do FuelCalculationController.
     */

    private static FuelCalculationController getFuelCalculationController(FuelCalculationService fuelCalculationService) {
        CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase = new CalculateFuelConsumptionUseCase(fuelCalculationService);
        FuelCalculationPresenter fuelCalculationPresenter = new FuelCalculationPresenter();
        String filePath = "fuel_calculations.csv";
        FuelCalculationGateway fuelCalculationGateway = new FuelCalculationCsvGatewayImpl(filePath);

        // Retorna a Instância do FuelCalculationController
        return new FuelCalculationController(fuelCalculationGateway, calculateFuelConsumptionUseCase, fuelCalculationPresenter, fuelCalculationGateway);
    }
}