package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    // ---------------- FEET TESTS ----------------

    @Test
    void givenSameFeetValue_whenCompared_shouldReturnTrue() {
        QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void givenDifferentFeetValue_whenCompared_shouldReturnFalse() {
        QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    // ---------------- INCHES TESTS ----------------

    @Test
    void givenSameInchesValue_whenCompared_shouldReturnTrue() {
        QuantityMeasurementApp.Inches inch1 = new QuantityMeasurementApp.Inches(1.0);
        QuantityMeasurementApp.Inches inch2 = new QuantityMeasurementApp.Inches(1.0);

        assertTrue(inch1.equals(inch2));
    }

    @Test
    void givenDifferentInchesValue_whenCompared_shouldReturnFalse() {
        QuantityMeasurementApp.Inches inch1 = new QuantityMeasurementApp.Inches(1.0);
        QuantityMeasurementApp.Inches inch2 = new QuantityMeasurementApp.Inches(2.0);

        assertFalse(inch1.equals(inch2));
    }

    @Test
    void givenNullInches_whenCompared_shouldReturnFalse() {
        QuantityMeasurementApp.Inches inch = new QuantityMeasurementApp.Inches(1.0);

        assertFalse(inch.equals(null));
    }

    @Test
    void givenDifferentTypeInches_whenCompared_shouldReturnFalse() {
        QuantityMeasurementApp.Inches inch = new QuantityMeasurementApp.Inches(1.0);

        assertFalse(inch.equals("1.0"));
    }

    @Test
    void givenSameReferenceInches_whenCompared_shouldReturnTrue() {
        QuantityMeasurementApp.Inches inch = new QuantityMeasurementApp.Inches(1.0);

        assertTrue(inch.equals(inch));
    }
}