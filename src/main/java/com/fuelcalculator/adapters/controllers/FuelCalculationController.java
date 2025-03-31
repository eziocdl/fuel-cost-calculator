package com.fuelcalculator.adapters.controllers;

import com.fuelcalculator.adapters.gateways.FuelCalculationGateway;
import com.fuelcalculator.adapters.presenters.FuelCalculationPresenter;
import com.fuelcalculator.usercases.CalculateFuelConsumptionUseCase;
import com.fuelcalculator.usercases.FuelCalculationInputDTO;
import com.fuelcalculator.usercases.FuelCalculationOutput;

import java.util.List;
import java.util.Optional;

public class FuelCalculationController {

    private final FuelCalculationGateway gateway;
    private final CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase;
    private final FuelCalculationPresenter fuelCalculationPresenter;

    private final FuelCalculationGateway fuelCalculationGateway;

    public FuelCalculationController(FuelCalculationGateway gateway, CalculateFuelConsumptionUseCase calculateFuelConsumptionUseCase, FuelCalculationPresenter fuelCalculationPresenter, FuelCalculationGateway fuelCalculationGateway) {
        this.gateway = gateway;
        this.calculateFuelConsumptionUseCase = calculateFuelConsumptionUseCase;
        this.fuelCalculationPresenter = fuelCalculationPresenter;
        this.fuelCalculationGateway = fuelCalculationGateway;
    }

    /**
     * Gera um relatrio explicando como o calculo de combustível foi feito.
     *
     * @param initialKilometers    quilômetros iniciais
     * @param litersFilled         litros de combustível abastecidos
     * @param finalKilometers      quilômetros finais
     * @param totalCost            custo total em reais
     * @return                      um relatrio explicando como o cálculo foi feito
     */

    public void handleCalculationRequest(double initialKilometers, double litersFilled, double finalKilometers, double totalCost) {
        FuelCalculationInputDTO calculationInput = new FuelCalculationInputDTO(initialKilometers, litersFilled, finalKilometers, totalCost);
        FuelCalculationOutput calculationOutput = calculateFuelConsumptionUseCase.execute(calculationInput);
        fuelCalculationPresenter.present(calculationOutput);


        fuelCalculationGateway.saveCalculation(calculationOutput);
    }

    public List<FuelCalculationOutput> getCalculations() {
        return gateway.getCalculations();
    }

    public Optional<FuelCalculationOutput> updateCalculation(FuelCalculationOutput output) {
        return gateway.updateCalculation(output);
    }

    public boolean deleteCalculation(FuelCalculationOutput output) {
        return gateway.deleteCalculation(output);
    }

    public List<FuelCalculationOutput> getCalculationByPeriod(int month, int year) {
        return gateway.getCalculationByPeriod(month, year);
    }
}