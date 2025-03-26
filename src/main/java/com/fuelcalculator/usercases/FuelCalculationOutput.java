package com.fuelcalculator.usercases;

import java.time.LocalDate;

public class FuelCalculationOutput {

    private Long id;


    private double consumption;
    private double costPerKilometer;

    private LocalDate calculationDate;

    public FuelCalculationOutput(Long id, double consumption, double costPerKilometer, LocalDate calculationDate) {
        this.id = id;
        this.consumption = consumption;
        this.costPerKilometer = costPerKilometer;
        this.calculationDate = calculationDate;
    }


    public Long getId() {
        return id;
    }

    public double getConsumption() {
        return consumption;
    }

    public double getCostPerKilometer() {
        return costPerKilometer;
    }

    public LocalDate getCalculationDate() {
        return calculationDate;
    }
}
