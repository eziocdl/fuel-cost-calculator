package com.fuelcalculator.domain.entities;

public class FuelCalculation {


    // atributos
    private double initialKilometeres;
    private double litersFilled;
    private double finalKilometers;
    private double totalCost;

   //constructors
    public FuelCalculation(double initialKilometeres, double litersFilled, double finalKilometers, double totalCost) {
        this.initialKilometeres = initialKilometeres;
        this.litersFilled = litersFilled;
        this.finalKilometers = finalKilometers;
        this.totalCost = totalCost;
    }


    public double getInitialKilometeres() {
        return initialKilometeres;
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
