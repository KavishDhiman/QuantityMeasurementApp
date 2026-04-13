package com.app.quantitymeasurement.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityDTOTest {

    @Test
    public void givenDTO_shouldStoreValues() {
        QuantityDTO dto = new QuantityDTO();
        dto.value = 5;
        dto.unit = "FEET";

        assertEquals(5.0, dto.value, 0.001);
        assertEquals("FEET", dto.unit);
    }
}