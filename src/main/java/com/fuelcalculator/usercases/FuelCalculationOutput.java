package com.fuelcalculator.usercases;

public class FuelCalculationOutput {

    private double consumption;
    private double costPerKilometer;

    public FuelCalculationOutput(double consumption, double costPerKilometer) {
        this.consumption = consumption;
        this.costPerKilometer = costPerKilometer;
    }

    public double getConsumption() {
        return consumption;
    }

    public double getCostPerKilometer() {
        return costPerKilometer;
    }
}
