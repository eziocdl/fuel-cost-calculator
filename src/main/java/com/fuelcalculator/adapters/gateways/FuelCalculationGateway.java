package com.fuelcalculator.adapters.gateways;

import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.util.List;

public interface FuelCalculationGateway {

    void saveCalculation(FuelCalculationOutput output);

    void getCalculations();

    List<FuelCalculationOutput> getCalculationByPeriod(int month, int year);
}
