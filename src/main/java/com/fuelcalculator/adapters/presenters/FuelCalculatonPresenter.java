package com.fuelcalculator.adapters.presenters;

import com.fuelcalculator.usercases.FuelCalculationOutput;

public class FuelCalculatonPresenter {

    public void present(FuelCalculationOutput output) {
        System.out.println("Consumo: " + output.getConsumption() + "km/l" );
        System.out.println("Custo por km: " + output.getCostPerKilometer() + "R$/km");
    }
}
