package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enums.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityModelTest {

    @Test
    void givenModel_whenValueAndUnitSet_shouldStoreCorrectly() {
        QuantityModel<LengthUnit> model = new QuantityModel<>();
        model.value = 10;
        model.unit = LengthUnit.FEET;

        assertEquals(10.0, model.value, 0.001);
        assertEquals("FEET", model.unit.getUnitName());
    }

    @Test
    void givenModel_withInchUnit_shouldStoreInchCorrectly() {
        QuantityModel<LengthUnit> model = new QuantityModel<>();
        model.value = 12;
        model.unit = LengthUnit.INCH;

        assertEquals(12.0, model.value, 0.001);
        assertEquals("INCH", model.unit.getUnitName());
    }
}