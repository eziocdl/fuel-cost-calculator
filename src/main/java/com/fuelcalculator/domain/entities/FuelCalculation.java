package com.fuelcalculator.domain.entities;

public class FuelCalculation {


    // atributos
    private double initialKilometers;
    private double litersFilled;
    private double finalKilometers;
    private double totalCost;

   //constructors
    public FuelCalculation(double initialKilometers, double litersFilled, double finalKilometers, double totalCost) {
        this.initialKilometers = initialKilometers;
        this.litersFilled = litersFilled;
        this.finalKilometers = finalKilometers;
        this.totalCost = totalCost;
    }


    public double getInitialKilometers() {
        return initialKilometers;
    }

    public double getLitersFilled() {
        return litersFilled;
    }

    public double getFinalKilometers() {
        return finalKilometers;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
