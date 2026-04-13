package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityLengthTest {

    private static final double EPSILON = 1e-4;

    // ----------------------
    // Equality Tests
    // ----------------------

    @Test
    void testEquality_FeetToFeet_SameValue() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(q1, q2);
    }

    @Test
    void testEquality_YardToFeet() {
        Quantity<LengthUnit> yard =
                new Quantity<>(1.0, LengthUnit.YARDS);

        Quantity<LengthUnit> feet =
                new Quantity<>(3.0, LengthUnit.FEET);

        assertEquals(yard, feet);
    }

    @Test
    void testEquality_CentimeterToInch() {
        Quantity<LengthUnit> cm =
                new Quantity<>(2.54, LengthUnit.CENTIMETERS);

        Quantity<LengthUnit> inch =
                new Quantity<>(1.0, LengthUnit.INCH);

        assertEquals(cm, inch);
    }

    @Test
    void testEquality_DifferentValue() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_NullComparison() {
        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(null, q);
    }

    // ----------------------
    // Conversion Tests
    // ----------------------

    @Test
    void testConversion_FeetToInches() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                feet.convertTo(LengthUnit.INCH);

        assertEquals(12.0, inches.getValue(), EPSILON);
    }

    @Test
    void testConversion_YardsToFeet() {
        Quantity<LengthUnit> yards =
                new Quantity<>(3.0, LengthUnit.YARDS);

        Quantity<LengthUnit> feet =
                yards.convertTo(LengthUnit.FEET);

        assertEquals(9.0, feet.getValue(), EPSILON);
    }

    @Test
    void testConversion_InchesToYards() {
        Quantity<LengthUnit> inches =
                new Quantity<>(36.0, LengthUnit.INCH);

        Quantity<LengthUnit> yards =
                inches.convertTo(LengthUnit.YARDS);

        assertEquals(1.0, yards.getValue(), EPSILON);
    }

    @Test
    void testConversion_CentimeterToInch() {
        Quantity<LengthUnit> cm =
                new Quantity<>(2.54, LengthUnit.CENTIMETERS);

        Quantity<LengthUnit> inch =
                cm.convertTo(LengthUnit.INCH);

        assertEquals(1.0, inch.getValue(), EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        Quantity<LengthUnit> feet =
                new Quantity<>(0.0, LengthUnit.FEET);

        Quantity<LengthUnit> inch =
                feet.convertTo(LengthUnit.INCH);

        assertEquals(0.0, inch.getValue(), EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        Quantity<LengthUnit> feet =
                new Quantity<>(-1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inch =
                feet.convertTo(LengthUnit.INCH);

        assertEquals(-12.0, inch.getValue(), EPSILON);
    }

    // ----------------------
    // Addition Tests
    // ----------------------

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(2.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                q1.add(q2);

        assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                feet.add(inches);

        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                inches.add(feet);

        assertEquals(new Quantity<>(24.0, LengthUnit.INCH), result);
    }

    @Test
    void testAddition_YardPlusFeet() {
        Quantity<LengthUnit> yard =
                new Quantity<>(1.0, LengthUnit.YARDS);

        Quantity<LengthUnit> feet =
                new Quantity<>(3.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                yard.add(feet);

        assertEquals(new Quantity<>(2.0, LengthUnit.YARDS), result);
    }

    @Test
    void testAddition_WithZero() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> zero =
                new Quantity<>(0.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                q1.add(zero);

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_NullSecondOperand() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null));
    }

    @Test
    void testAddition_ExplicitTargetUnit() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                q1.add(q2, LengthUnit.YARDS);

        assertEquals(new Quantity<>(0.6666666667, LengthUnit.YARDS), result);
    }
}