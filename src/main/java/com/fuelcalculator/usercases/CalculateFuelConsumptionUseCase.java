package com.fuelcalculator.usercases;

import com.fuelcalculator.domain.entities.FuelCalculation;
import com.fuelcalculator.domain.services.FuelCalculationService;

import java.time.LocalDate;

/**
 * Caso de uso para calcular o consumo de combustível e o custo por quilômetro.
 * Este caso de uso orquestra a lógica de domínio para realizar o cálculo e retorna um objeto de saída.
 */
public class CalculateFuelConsumptionUseCase {

    private final FuelCalculationService fuelCalculationService;

    /**
     * Construtor que recebe o serviço de cálculo de combustível.
     * @param fuelCalculationService O serviço de cálculo de combustível.
     */
    public CalculateFuelConsumptionUseCase(FuelCalculationService fuelCalculationService) {
        this.fuelCalculationService = fuelCalculationService;
    }

    /**
     * Executa o cálculo do consumo de combustível e do custo por quilômetro com base na entrada fornecida.
     * @param input O objeto de entrada contendo os dados necessários para o cálculo.
     * @return Um objeto FuelCalculationOutput contendo os resultados do cálculo.
     */
    public FuelCalculationOutput execute(FuelCalculationInputDTO input) {
        FuelCalculation calculation = new FuelCalculation(
                input.getInitialKilometers(),
                input.getLittersFilled(),
                input.getFinalKilometers(),
                input.getTotalCost()
        );

        double consumption = fuelCalculationService.calculateConsumption(calculation);
        double costPerKilometer = fuelCalculationService.calculateCostPerKilometers(calculation);

        // Gera um ID único (uma estratégia de geração de ID mais robusta pode ser necessária em produção).
        long id = System.currentTimeMillis();
        // Obtém a data atual.
        LocalDate calculationDate = LocalDate.now();

        return new FuelCalculationOutput(id, consumption, costPerKilometer, calculationDate);
    }
}