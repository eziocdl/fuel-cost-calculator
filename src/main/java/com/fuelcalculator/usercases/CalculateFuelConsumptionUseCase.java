package com.fuelcalculator.usercases;

import com.fuelcalculator.domain.entities.FuelCalculation;
import com.fuelcalculator.domain.services.FuelCalculationService;

public class CalculateFuelConsumptionUseCase {

    private final FuelCalculationService fuelCalculationService;

    //
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

        return new FuelCalculationOutput(consumption, costPerKilometer);
    }
}
