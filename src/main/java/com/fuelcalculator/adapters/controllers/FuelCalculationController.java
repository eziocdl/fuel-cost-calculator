package com.fuelcalculator.adapters.controllers;

import com.fuelcalculator.adapters.presenters.FuelCalculatonPresenter;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;
import com.fuelcalculator.usercases.FuelCalculationInput;
import com.fuelcalculator.usercases.FuelCalculationOutput;

public class FuelCalculationController {

    private final CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase;
    private final FuelCalculatonPresenter fuelCalculatonPresenter;

    public FuelCalculationController(CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase, FuelCalculatonPresenter fuelCalculatonPresenter) {
        this.calculateFuelConsumptionUseCase = calculateFuelConsumptionUseCase;
        this.fuelCalculatonPresenter = fuelCalculatonPresenter;
    }

    public void handleCalculationRequest(double initialKilometers, double litersFilled, double finalKilometers, double totalCost) {
        FuelCalculationInput input = new FuelCalculationInput(initialKilometers, litersFilled, finalKilometers, totalCost);
        FuelCalculationOutput output = calculateFuelConsumptionUseCase.execute(input);
        fuelCalculatonPresenter.present(output);
    }
}
