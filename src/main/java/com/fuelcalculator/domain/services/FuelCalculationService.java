package com.fuelcalculator.domain.services;

import com.fuelcalculator.domain.entities.FuelCalculation;

public class FuelCalculationService {

    public double calculateConsumption(FuelCalculation calculation) {
        return (calculation.getFinalKilometers() - calculation.getInitialKilometers()) / calculation.getLitersFilled();
    }
    public double calculateCostPerKilometers(FuelCalculation calculation) {
        return calculation.getTotalCost() / (calculation.getFinalKilometers() - calculation.getInitialKilometers());
    }
}
