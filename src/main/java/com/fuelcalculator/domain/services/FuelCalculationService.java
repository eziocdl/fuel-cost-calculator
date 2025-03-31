package com.fuelcalculator.domain.services;

import com.fuelcalculator.domain.entities.FuelCalculation;

/**
 * Serviço responsável por realizar os cálculos de consumo de combustível e custo por quilômetro.
 */

public class FuelCalculationService {


    /**
     * Calcula o consumo de combustível em km/l.
     *
     * @param calculation Dados do cálculo de combustível.
     * @return Consumo de combustível em km/l.
     */

    public double calculateConsumption(FuelCalculation calculation) {
        return (calculation.getFinalKilometers() - calculation.getInitialKilometers()) / calculation.getLitersFilled();

        /**
         * Calcula o custo por quilômetro em R$.
         *
         * @param calculation Dados do cálculo de combustível.
         * @return Custo por quilômetro em R$.
         */
    }
    public double calculateCostPerKilometers(FuelCalculation calculation) {
        return calculation.getTotalCost() / (calculation.getFinalKilometers() - calculation.getInitialKilometers());
    }
}
