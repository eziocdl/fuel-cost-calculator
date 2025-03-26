package com.fuelcalculator.infraestructure.gatewayimp;

import com.fuelcalculator.usercases.FuelCalculationOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FuelCalculationCsvGatewayImplTest {

    private FuelCalculationCsvGatewayImpl gateway;
    private final String filePath = "fuel_calculations.csv"; // Caminho do arquivo ajustado

    @BeforeEach
    public void setUp() throws Exception {
        // Criar um arquivo de teste vazio
        boolean created = new File(filePath).createNewFile();
        if (!created) {
            System.err.println("Falha ao criar o arquivo de teste: " + filePath);
        }
        gateway = new FuelCalculationCsvGatewayImpl(filePath);
    }

    @Test
    public void saveCalculation_shouldSaveCalculationToFile() {
        FuelCalculationOutput calculation = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.now());
        Optional<FuelCalculationOutput> savedCalculation = gateway.saveCalculation(calculation);
        assertTrue(savedCalculation.isPresent());
        assertEquals(calculation, savedCalculation.get());
    }

    @Test
    public void getCalculations_shouldReturnAllCalculationsFromFile() {
        FuelCalculationOutput calculation1 = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.now());
        FuelCalculationOutput calculation2 = new FuelCalculationOutput(2L, 20.0, 10.0, LocalDate.now().plusDays(1));
        gateway.saveCalculation(calculation1);
        gateway.saveCalculation(calculation2);

        List<FuelCalculationOutput> calculations = gateway.getCalculations();
        System.out.println("Calculations retornadas: " + calculations); // Log adicionado
        assertEquals(2, calculations.size());
        assertEquals(calculation1, calculations.get(0));
        assertEquals(calculation2, calculations.get(1));
    }

    @Test
    public void getCalculationByPeriod_shouldReturnCalculationsInPeriod() {
        FuelCalculationOutput calculation1 = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.of(2024, 5, 1));
        FuelCalculationOutput calculation2 = new FuelCalculationOutput(2L, 20.0, 10.0, LocalDate.of(2024, 5, 10));
        FuelCalculationOutput calculation3 = new FuelCalculationOutput(3L, 30.0, 15.0, LocalDate.of(2024, 6, 1));
        gateway.saveCalculation(calculation1);
        gateway.saveCalculation(calculation2);
        gateway.saveCalculation(calculation3);

        List<FuelCalculationOutput> calculations = gateway.getCalculationByPeriod(5, 2024);
        assertEquals(2, calculations.size());
        assertEquals(calculation1, calculations.get(0));
        assertEquals(calculation2, calculations.get(1));
    }

    @Test
    public void updateCalculation_shouldUpdateCalculationInFile() {
        FuelCalculationOutput calculation1 = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.now());
        gateway.saveCalculation(calculation1);

        FuelCalculationOutput updatedCalculation = new FuelCalculationOutput(1L, 20.0, 10.0, LocalDate.now().plusDays(1));
        Optional<FuelCalculationOutput> result = gateway.updateCalculation(updatedCalculation);
        assertTrue(result.isPresent());
        assertEquals(updatedCalculation, result.get());
    }

    @Test
    public void deleteCalculation_shouldDeleteCalculationFromFile() {
        FuelCalculationOutput calculation1 = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.now());
        gateway.saveCalculation(calculation1);

        boolean result = gateway.deleteCalculation(calculation1);
        assertTrue(result);
    }

    @Test
    public void getCalculations_shouldReturnEmptyListIfFileIsEmpty() {
        // Excluir o arquivo de teste e criar um novo vazio
        new File(filePath).delete();
        try {
            new File(filePath).createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<FuelCalculationOutput> calculations = gateway.getCalculations();
        assertTrue(calculations.isEmpty());
    }
}