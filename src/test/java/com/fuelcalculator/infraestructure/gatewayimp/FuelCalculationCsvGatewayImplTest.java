package com.fuelcalculator.infraestructure.gatewayimp;

import com.fuelcalculator.usercases.FuelCalculationOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FuelCalculationCsvGatewayImplTest {

    private FuelCalculationCsvGatewayImpl gateway;
    private final String TEST_FILE = "test_fuel_calculations.csv";
    private final String TEST_ID_FILE = "test_fuel_calculations_id.txt";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get("src/main/resources", TEST_FILE));
        Files.deleteIfExists(Paths.get("src/main/resources", TEST_ID_FILE));
        gateway = new FuelCalculationCsvGatewayImpl(TEST_FILE, TEST_ID_FILE);
    }

    @Test
    void saveCalculation_shouldSaveCalculationToFile() throws IOException {
        FuelCalculationOutput output = new FuelCalculationOutput(null, 10.0, 5.0, LocalDate.of(2024, 5, 1));
        Optional<FuelCalculationOutput> savedOutput = gateway.saveCalculation(output);

        assertTrue(savedOutput.isPresent());
        assertEquals(1L, savedOutput.get().getId());

        List<String> lines = Files.readAllLines(Paths.get("src/main/resources", TEST_FILE));
        assertEquals("1,10.0,5.0,01-05-2024", lines.get(0));
    }

    @Test
    void getCalculations_shouldReturnAllCalculationsFromFile() throws IOException {
        createTestFileWithData();
        List<FuelCalculationOutput> calculations = gateway.getCalculations();

        assertEquals(2, calculations.size());
        assertEquals(1L, calculations.get(0).getId());
        assertEquals(2L, calculations.get(1).getId());
    }



    @Test
    void updateCalculation_shouldUpdateCalculationInFile() throws IOException {
        createTestFileWithData();
        FuelCalculationOutput updatedOutput = new FuelCalculationOutput(1L, 12.0, 6.0, LocalDate.of(2024, 5, 1));
        Optional<FuelCalculationOutput> result = gateway.updateCalculation(updatedOutput);

        assertTrue(result.isPresent());
        assertEquals(12.0, result.get().getConsumption());

        List<String> lines = Files.readAllLines(Paths.get("src/main/resources", TEST_FILE));
        assertEquals(2, lines.size());
        String[] expectedLine = lines.get(0).split(",");
        assertEquals("1", expectedLine[0]);
        assertEquals("12.0", expectedLine[1]);
        assertEquals("6.0", expectedLine[2]);
        assertEquals("01-05-2024", expectedLine[3]);
    }
    @Test
    void deleteCalculation_shouldDeleteCalculationFromFile() throws IOException {
        createTestFileWithData();
        FuelCalculationOutput outputToDelete = new FuelCalculationOutput(1L, 10.0, 5.0, LocalDate.of(2024, 5, 1));
        boolean deleted = gateway.deleteCalculation(outputToDelete);

        assertTrue(deleted);
        assertEquals(1, gateway.getCalculations().size());

        List<String> lines = Files.readAllLines(Paths.get("src/main/resources", TEST_FILE));
        assertEquals(1, lines.size());
        String[] expectedLine = lines.get(0).split(",");
        assertEquals("2", expectedLine[0]);
        assertEquals("20.0", expectedLine[1]);
        assertEquals("10.0", expectedLine[2]);
        assertEquals("02-05-2024", expectedLine[3]);
    }

    @Test
    void getCalculations_shouldReturnEmptyListIfFileIsEmpty() {
        List<FuelCalculationOutput> calculations = gateway.getCalculations();
        assertTrue(calculations.isEmpty());
    }

    private void createTestFileWithData() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get("src/main/resources", TEST_FILE).toFile()))) {
            writer.write("1,10.0,5.0,01-05-2024\n");
            writer.write("2,20.0,10.0,02-05-2024\n");
        }
    }
}