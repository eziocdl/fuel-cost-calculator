package com.fuelcalculator.usercases;

/**
 * Objeto de entrada para o caso de uso de cálculo de consumo de combustível.
 * Contém os dados necessários para realizar o cálculo.
 */
public class FuelCalculationInputDTO {

    private double initialKilometers;
    private double littersFilled;
    private double finalKilometers;
    private double totalCost;

    /**
     * Construtor que inicializa o objeto de entrada com os dados fornecidos.
     * @param initialKilometers Quilometragem inicial do veículo.
     * @param littersFilled Litros de combustível abastecidos.
     * @param finalKilometers Quilometragem final do veículo.
     * @param totalCost Custo total do abastecimento.
     */
    public FuelCalculationInputDTO(double initialKilometers, double littersFilled, double finalKilometers, double totalCost) {
        this.initialKilometers = initialKilometers;
        this.littersFilled = littersFilled;
        this.finalKilometers = finalKilometers;
        this.totalCost = totalCost;
    }

    /**
     * Obtém a quilometragem inicial do veículo.
     * @return A quilometragem inicial.
     */
    public double getInitialKilometers() {
        return initialKilometers;
    }

    /**
     * Obtém os litros de combustível abastecidos.
     * @return Os litros abastecidos.
     */
    public double getLittersFilled() {
        return littersFilled;
    }

    /**
     * Obtém a quilometragem final do veículo.
     * @return A quilometragem final.
     */
    public double getFinalKilometers() {
        return finalKilometers;
    }

    /**
     * Obtém o custo total do abastecimento.
     * @return O custo total.
     */
    public double getTotalCost() {
        return totalCost;
    }
}



