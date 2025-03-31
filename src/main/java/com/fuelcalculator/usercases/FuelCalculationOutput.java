package com.fuelcalculator.usercases;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Objeto de saída para o caso de uso de cálculo de consumo de combustível.
 * Contém os resultados do cálculo e informações adicionais.
 */
public class FuelCalculationOutput {

    private Long id;
    private double consumption;
    private double costPerKilometer;
    private LocalDate calculationDate;

    /**
     * Construtor para inicializar o objeto de saída com ID, consumo, custo por quilômetro e data.
     * @param id O ID único do cálculo.
     * @param consumption O consumo de combustível em litros por quilômetro.
     * @param costPerKilometer O custo por quilômetro.
     * @param calculationDate A data do cálculo.
     */
    public FuelCalculationOutput(Long id, double consumption, double costPerKilometer, LocalDate calculationDate) {
        this.id = id;
        this.consumption = consumption;
        this.costPerKilometer = costPerKilometer;
        this.calculationDate = calculationDate;
    }

    /**
     * Construtor para inicializar o objeto de saída com ID, quilometragem inicial, litros abastecidos, quilometragem final, custo total e data.
     * Calcula o consumo e o custo por quilômetro com base nos dados fornecidos.
     * @param id O ID único do cálculo.
     * @param initialKilometers A quilometragem inicial.
     * @param litersFilled Os litros abastecidos.
     * @param finalKilometers A quilometragem final.
     * @param totalCost O custo total.
     * @param calculationDate A data do cálculo.
     */
    public FuelCalculationOutput(Long id, double initialKilometers, double litersFilled, double finalKilometers, double totalCost, LocalDate calculationDate) {
        this.id = id;
        this.consumption = litersFilled / (finalKilometers - initialKilometers);
        this.costPerKilometer = totalCost / (finalKilometers - initialKilometers);
        this.calculationDate = calculationDate;
    }

    /**
     * Obtém o ID único do cálculo.
     * @return O ID do cálculo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtém o consumo de combustível em litros por quilômetro.
     * @return O consumo de combustível.
     */
    public double getConsumption() {
        return consumption;
    }

    /**
     * Obtém o custo por quilômetro.
     * @return O custo por quilômetro.
     */
    public double getCostPerKilometer() {
        return costPerKilometer;
    }

    /**
     * Obtém a data do cálculo.
     * @return A data do cálculo.
     */
    public LocalDate getCalculationDate() {
        return calculationDate;
    }

    /**
     * Compara este objeto com outro objeto para verificar se são iguais.
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
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

    /**
     * Calcula o código hash para este objeto.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, consumption, costPerKilometer, calculationDate);
    }

    /**
     * Retorna uma representação em String deste objeto.
     * @return Uma String representando o objeto.
     */
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