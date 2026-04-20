package com.app.quantitymeasurement.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementEntityTest {

    @Test
    void givenEntity_whenBuiltWithBuilderPattern_shouldStoreCorrectValues() {
        QuantityMeasurementEntity entity = QuantityMeasurementEntity.builder()
                .operation("ADD")
                .resultValue(20.0)
                .value1(1.0)
                .unit1("FEET")
                .value2(12.0)
                .unit2("INCH")
                .build();

        assertEquals("ADD", entity.getOperation());
        assertEquals(20.0, entity.getResultValue(), 0.001);
        assertEquals("FEET", entity.getUnit1());
        assertEquals("INCH", entity.getUnit2());
    }

    @Test
    void givenTwoEntities_whenSameValues_shouldBeEqual() {
        QuantityMeasurementEntity e1 = QuantityMeasurementEntity.builder()
                .operation("COMPARE")
                .resultValue(1.0)
                .unit1("KILOGRAM")
                .unit2("GRAM")
                .build();

        QuantityMeasurementEntity e2 = QuantityMeasurementEntity.builder()
                .operation("COMPARE")
                .resultValue(1.0)
                .unit1("KILOGRAM")
                .unit2("GRAM")
                .build();

        assertEquals(e1, e2);
    }
}