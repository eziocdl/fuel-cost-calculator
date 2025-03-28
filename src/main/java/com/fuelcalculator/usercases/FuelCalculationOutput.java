package com.fuelcalculator.usercases;

import java.time.LocalDate;
import java.util.Objects;

public class FuelCalculationOutput {

    private Long id;
    private double consumption;
    private double costPerKilometer;
    private LocalDate calculationDate;

    // Construtor com quatro argumentos (ID, consumo, custo por quilômetro, data)
    public FuelCalculationOutput(Long id, double consumption, double costPerKilometer, LocalDate calculationDate) {
        this.id = id;
        this.consumption = consumption;
        this.costPerKilometer = costPerKilometer;
        this.calculationDate = calculationDate;
    }

    // Novo construtor com seis argumentos (ID, quilometragem inicial, litros abastecidos, quilometragem final, custo total, data)
    public FuelCalculationOutput(Long id, double initialKilometers, double litersFilled, double finalKilometers, double totalCost, LocalDate calculationDate) {
        this.id = id;
        this.consumption = litersFilled / (finalKilometers - initialKilometers); // Calcula o consumo
        this.costPerKilometer = totalCost / (finalKilometers - initialKilometers); // Calcula o custo por quilômetro
        this.calculationDate = calculationDate;
    }


    // Getters...

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelCalculationOutput that = (FuelCalculationOutput) o;
        return Double.compare(that.consumption, consumption) == 0 &&
                Double.compare(that.costPerKilometer, costPerKilometer) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(calculationDate, that.calculationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consumption, costPerKilometer, calculationDate);
    }

    @Override
    public String toString() {
        return "FuelCalculationOutput{" +
                "id=" + id +
                ", consumption=" + consumption +
                ", costPerKilometer=" + costPerKilometer +
                ", calculationDate=" + calculationDate +
                '}';
    }
}