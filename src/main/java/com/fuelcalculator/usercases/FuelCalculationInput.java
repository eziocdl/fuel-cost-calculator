package com.fuelcalculator.usercases;

public class FuelCalculationInput {

    private double initialKilometers;
    private double littersFilled;
    private double finalKilometers;
    private double totalCost;

    public FuelCalculationInput(double initialKilometers, double littersFilled, double finalKilometers, double totalCost) {
        this.initialKilometers = initialKilometers;
        this.littersFilled = littersFilled;
        this.finalKilometers = finalKilometers;
        this.totalCost = totalCost;
    }


    public double getInitialKilometers() {
        return initialKilometers;
    }

    public double getLittersFilled() {
        return littersFilled;
    }

    public double getFinalKilometers() {
        return finalKilometers;
    }

    public double getTotalCost() {
        return totalCost;
    }
}



