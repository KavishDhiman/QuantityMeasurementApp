package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class QuantityMeasurementControllerTest {

    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @Before
    public void setup() {
        service = Mockito.mock(IQuantityMeasurementService.class);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    public void givenQuantities_whenCompared_shouldReturnTrue() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1;
        q1.unit = "FEET";

        QuantityDTO q2 = new QuantityDTO();
        q2.value = 12;
        q2.unit = "INCH";

        when(service.compare(q1, q2)).thenReturn(true);

        assertTrue(controller.compare(q1, q2));
    }

    @Test
    public void givenQuantities_whenAdded_shouldReturnCorrectSum() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 2;
        q1.unit = "FEET";

        QuantityDTO q2 = new QuantityDTO();
        q2.value = 24;
        q2.unit = "INCH";

        when(service.add(q1, q2)).thenReturn(4.0);

        assertEquals(4.0, controller.add(q1, q2), 0.001);
    }
}