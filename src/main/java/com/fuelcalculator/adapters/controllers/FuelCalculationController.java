package com.fuelcalculator.adapters.controllers;

import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculationPresenter;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;
import com.fuelcalculator.usercases.FuelCalculationInput;
import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.util.List;
import java.util.Optional;

public class FuelCalculationController {

    private final FuelCalculationGateway gateway;
    private final CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase;
    private final FuelCalculationPresenter fuelCalculationPresenter;

    private final FuelCalculationGateway fuelCalculationGateway;

    public FuelCalculationController(FuelCalculationGateway gateway, CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase, FuelCalculationPresenter fuelCalculationPresenter, FuelCalculationGateway fuelCalculationGateway) {
        this.gateway = gateway;
        this.calculateFuelConsumptionUseCase = calculateFuelConsumptionUseCase;
        this.fuelCalculationPresenter = fuelCalculationPresenter;
        this.fuelCalculationGateway = fuelCalculationGateway;
    }

    public void handleCalculationRequest(double initialKilometers, double litersFilled, double finalKilometers, double totalCost) {
        FuelCalculationInput input = new FuelCalculationInput(initialKilometers, litersFilled, finalKilometers, totalCost);
        FuelCalculationOutput output = calculateFuelConsumptionUseCase.execute(input);
        fuelCalculationPresenter.present(output);

        // Adicionando a chamada para salvar o cálculo no arquivo CSV
        fuelCalculationGateway.saveCalculation(output);
    }
    public List<FuelCalculationOutput> getCalculations() {
        return gateway.getCalculations();
    }

    public Optional<FuelCalculationOutput> updateCalculation(FuelCalculationOutput output) {
        return gateway.updateCalculation(output);
    }

    public boolean deleteCalculation(FuelCalculationOutput output) {
        return gateway.deleteCalculation(output);
    }

    public List<FuelCalculationOutput> getCalculationByPeriod(int month, int year) {
        return gateway.getCalculationByPeriod(month, year);
    }
}