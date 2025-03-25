package com.fuelcalculator.infraestructure.gatewayimp;

import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.util.List;

public class FuelGatewayImpl implements FuelCalculationGateway {

    @Override
    public void saveCalculation(FuelCalculationOutput output) {
        // TODO para salvar o cálculo
        throw new UnsupportedOperationException("Método não implementado.");
    }

    @Override
    public List<FuelCalculationOutput> getCalculations() {
        // TODO método para buscar todos os cálculos

        throw new UnsupportedOperationException("Método não implementado");
    }


    @Override
    public List<FuelCalculationOutput> getCalculationByPeriod(int month, int year) {
        // TODO método para cálculos por período
        throw new UnsupportedOperationException("Método ainda não implementado");
    }
}
