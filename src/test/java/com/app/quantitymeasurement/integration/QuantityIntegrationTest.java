package com.app.quantitymeasurement.integration;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityIntegrationTest {

    private QuantityMeasurementController controller;

    @Before
    public void setup() {
        IQuantityMeasurementRepository repo = QuantityMeasurementDatabaseRepository.getInstance();
        repo.deleteAll();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repo);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    public void givenFullFlow_whenAdd_shouldWorkEndToEnd() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1;
        q1.unit = "FEET";

        QuantityDTO q2 = new QuantityDTO();
        q2.value = 12;
        q2.unit = "INCH";

        double result = controller.add(q1, q2);

        assertEquals(2.0, result, 0.001);
    }
}