package com.fuelcalculator.adapters.presenters;

import com.fuelcalculator.usercases.FuelCalculationOutput;


/**
 * Presenter para apresentar os resultados do cálculo de combustível.
 */

public class FuelCalculationPresenter {

    /**
     * Apresenta os resultados do cálculo de combustível.
     *
     * @param output Objeto de saída do cálculo de combustível.
     */

    public void present(FuelCalculationOutput output) {
        System.out.println("Consumo: " + output.getConsumption() + " km/l" );
        System.out.println("Custo por km em R$: " + output.getCostPerKilometer());
    }
}
