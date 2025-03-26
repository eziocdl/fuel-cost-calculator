package com.fuelcalculator.usercases;

import com.fuelcalculator.domain.entities.FuelCalculation;
import com.fuelcalculator.domain.services.FuelCalculationService;

import java.time.LocalDate;

public class CalculateFuelConsumptionUseCase {

    private final FuelCalculationService fuelCalculationService;

    public CalculateFuelConsumptionUseCase(FuelCalculationService fuelCalculationService) {
        this.fuelCalculationService = fuelCalculationService;
    }

    public FuelCalculationOutput execute(FuelCalculationInput input) {
        FuelCalculation calculation = new FuelCalculation(
                input.getInitialKilometers(),
                input.getLittersFilled(),
                input.getFinalKilometers(),
                input.getTotalCost()
        );

        double consumption = fuelCalculationService.calculateConsumption(calculation);
        double costPerKilometer = fuelCalculationService.calculateCostPerKilometers(calculation);

        // Generate a unique ID (you may need a more robust ID generation strategy)
        long id = System.currentTimeMillis();
        // Get the current date
        LocalDate calculationDate = LocalDate.now();

        return new FuelCalculationOutput(id, consumption, costPerKilometer, calculationDate);
    }
}