package com.app.quantitymeasurement.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityDTOTest {

    @Test
    void givenDTO_whenCreatedWithAllArgs_shouldStoreValues() {
        QuantityDTO dto = new QuantityDTO(5.0, "FEET");

        assertEquals(5.0, dto.getValue(), 0.001);
        assertEquals("FEET", dto.getUnit());
    }

    @Test
    void givenDTO_whenCreatedWithNoArgs_shouldHaveDefaults() {
        QuantityDTO dto = new QuantityDTO();
        dto.setValue(10.0);
        dto.setUnit("INCH");

        assertEquals(10.0, dto.getValue(), 0.001);
        assertEquals("INCH", dto.getUnit());
    }

    @Test
    void givenTwoDTOs_whenSameValues_shouldBeEqual() {
        QuantityDTO dto1 = new QuantityDTO(3.0, "YARD");
        QuantityDTO dto2 = new QuantityDTO(3.0, "YARD");
        assertEquals(dto1, dto2);
    }
}