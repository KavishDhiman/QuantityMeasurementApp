package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityArithmeticTest {

    private static final double EPSILON = 1e-6;

    // ----------------------
    // Subtraction Tests
    // ----------------------

    @Test
    void testSubtraction_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    void testSubtraction_DifferentUnits() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), result);
    }

    @Test
    void testSubtraction_NegativeResult() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), result);
    }

    @Test
    void testSubtraction_ResultZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(120.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(new Quantity<>(0.0, LengthUnit.FEET), result);
    }

    // ----------------------
    // Division Tests
    // ----------------------

    @Test
    void testDivision_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testDivision_DifferentUnits() {
        Quantity<LengthUnit> q1 = new Quantity<>(24.0, LengthUnit.INCH);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testDivision_ByZero_Throws() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class,
                () -> q1.divide(q2));
    }

    @Test
    void testSubtraction_CrossCategory_Throws() {
        Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> length.subtract((Quantity) weight));
    }
}