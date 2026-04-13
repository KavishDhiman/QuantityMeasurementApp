package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enums.LengthUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityModelTest {

    @Test
    public void givenModel_shouldStoreValueAndUnit() {
        QuantityModel<LengthUnit> model = new QuantityModel<>();
        model.value = 10;
        model.unit = LengthUnit.FEET;

        assertEquals(10.0, model.value, 0.001);
        assertEquals("FEET", model.unit.getUnitName());
    }
}