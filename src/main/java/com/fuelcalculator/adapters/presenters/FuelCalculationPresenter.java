package com.fuelcalculator.adapters.presenters;

import com.fuelcalculator.usercases.FuelCalculationOutput;

public class FuelCalculationPresenter {

    public void present(FuelCalculationOutput output) {
        System.out.println("Consumo: " + output.getConsumption() + " km/l" );
        System.out.println("Custo por km em R$: " + output.getCostPerKilometer());
    }
}
