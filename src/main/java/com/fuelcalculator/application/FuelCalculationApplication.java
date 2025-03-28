package com.fuelcalculator.application;

import com.fuelcalculator.adapters.controllers.FuelCalculationController;
import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculationPresenter;
import com.fuelcalculator.domain.services.FuelCalculationService;
import com.fuelcalculator.infraestructure.gatewayimp.FuelCalculationCsvGatewayImpl;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;

public class FuelCalculationApplication {

    public static void main(String[] args) {
        FuelCalculationService fuelCalculationService = new FuelCalculationService();
        FuelCalculationController fuelCalculationController = getFuelCalculationController(fuelCalculationService);

        // Simulação de entrada do usuário

        double initialKilometers = 1000;
        double litersFilled = 50;
        double finalKilometers = 1500;
        double totalCost = 250;

        fuelCalculationController.handleCalculationRequest(initialKilometers, litersFilled, finalKilometers, totalCost);

    }

    private static FuelCalculationController getFuelCalculationController(FuelCalculationService fuelCalculationService) {
        CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase = new CalculateFuelConsumptionUseCase(fuelCalculationService);
        FuelCalculationPresenter fuelCalculationPresenter = new FuelCalculationPresenter();
        String filePath = "fuel_calculations.csv";
        FuelCalculationGateway fuelCalculationGateway = new FuelCalculationCsvGatewayImpl(filePath);

        // Correção: Passar os quatro argumentos na ordem correta
        return new FuelCalculationController(fuelCalculationGateway, calculateFuelConsumptionUseCase, fuelCalculationPresenter, fuelCalculationGateway);
    }
}