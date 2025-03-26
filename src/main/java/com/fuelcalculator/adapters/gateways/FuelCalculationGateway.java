package com.fuelcalculator.adapters.gateways;

import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.util.List;
import java.util.Optional;

public interface FuelCalculationGateway {

   Optional<FuelCalculationOutput> saveCalculation(FuelCalculationOutput output);
   List<FuelCalculationOutput> getCalculations();

   List<FuelCalculationOutput> getCalculationByPeriod(int month, int year);
   Optional<FuelCalculationOutput> updateCalculation(FuelCalculationOutput output);

   boolean deleteCalculation(FuelCalculationOutput output);
}
