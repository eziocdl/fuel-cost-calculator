package com.fuelcalculator.application;

import com.fuelcalculator.adapters.controllers.FuelCalculationController;
import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculatonPresenter;
import com.fuelcalculator.domain.services.FuelCalculationService;
import com.fuelcalculator.infraestructure.gatewayimp.FueCalculationGatewayImpl;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;

public class FuelCalculationApplication {

    public static void main(String[] args) {
        FuelCalculationService fuelCalculationService = new FuelCalculationService();
        CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase = new CalculateFuelConsumptionUseCase(fuelCalculationService);
        FuelCalculatonPresenter fuelCalculationPresenter = new FuelCalculatonPresenter();
        FuelCalculationGateway fuelCalculationGateway = new FueCalculationGatewayImpl();
        FuelCalculationController fuelCalculationController = new FuelCalculationController(calculateFuelConsumptionUseCase,
                fuelCalculationPresenter, fuelCalculationGateway);

        // Simulação de entrada do usuário

        double initialKilometers = 1000;
        double litersFilled = 50;
        double finalKilometers = 1500;
        double totalCost = 250;

        fuelCalculationController.handleCalculationRequest(initialKilometers, litersFilled, finalKilometers, totalCost);

    }
}
