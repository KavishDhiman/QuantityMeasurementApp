package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementRepository repo;
    private IQuantityMeasurementService service;

    @Before
    public void setup() {
        repo = Mockito.mock(IQuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(repo);
    }

    @Test
    public void givenSameLengthUnits_whenCompared_shouldReturnTrue() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1;
        q1.unit = "FEET";

        QuantityDTO q2 = new QuantityDTO();
        q2.value = 12;
        q2.unit = "INCH";

        assertTrue(service.compare(q1, q2));
    }

    @Test
    public void givenLengthUnits_whenAdded_shouldReturnSumInBaseUnit() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1;
        q1.unit = "FEET";

        QuantityDTO q2 = new QuantityDTO();
        q2.value = 12;
        q2.unit = "INCH";

        double result = service.add(q1, q2);

        assertEquals(2.0, result, 0.001); // 1 ft + 12 in = 2 ft
    }

    @Test(expected = RuntimeException.class)
    public void givenDifferentTypes_whenAdd_shouldThrowException() {
        QuantityDTO q1 = new QuantityDTO();
        q1.unit = "FEET";
        q1.value = 1;

        QuantityDTO q2 = new QuantityDTO();
        q2.unit = "KILOGRAM";
        q2.value = 1;

        service.add(q1, q2);
    }
}