package com.app.quantitymeasurement.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementEntityTest {

    @Test
    public void givenEntity_whenCreated_shouldStoreResult() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(20.0, "ADD");

        assertEquals(20.0, entity.resultValue, 0.001);
        assertEquals("ADD", entity.operation);
    }

    @Test
    public void givenErrorEntity_shouldMarkError() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity("Error occurred");

        assertTrue(entity.isError);
    }
}