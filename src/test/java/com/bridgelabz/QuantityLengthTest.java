package com.bridgelabz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityLengthTest {

    private static final double EPSILON = 1e-6;

    // ----------------------
    // Equality Tests
    // ----------------------

    @Test
    void testEquality_FeetToFeet_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        assertEquals(q1, q2);
    }

    @Test
    void testEquality_YardToFeet() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

        assertEquals(yard, feet);
    }

    @Test
    void testEquality_CentimeterToInch() {
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);

        assertEquals(cm, inch);
    }

    @Test
    void testEquality_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);
        assertNotEquals(null, q);
    }

    // ----------------------
    // Conversion Tests
    // ----------------------

    @Test
    void testConversion_FeetToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(12.0, result, EPSILON);
    }

    @Test
    void testConversion_YardsToFeet() {
        double result = QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        assertEquals(9.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToYards() {
        double result = QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.YARDS);
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testConversion_CentimeterToInch() {
        double result = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCH);
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {
        double original = 5.0;

        double toInch = QuantityLength.convert(original, LengthUnit.FEET, LengthUnit.INCH);
        double backToFeet = QuantityLength.convert(toInch, LengthUnit.INCH, LengthUnit.FEET);

        assertEquals(original, backToFeet, EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        double result = QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(0.0, result, EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        double result = QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(-12.0, result, EPSILON);
    }

    @Test
    void testConversion_NullUnit_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(1.0, null, LengthUnit.FEET));
    }

    @Test
    void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
    }
    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }
    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = feet.add(inches);

        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }
    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        QuantityLength inches = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength result = inches.add(feet);

        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
    }
    @Test
    void testAddition_YardPlusFeet() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength result = yard.add(feet);

        assertEquals(new QuantityLength(2.0, LengthUnit.YARDS), result);
    }
    @Test
    void testAddition_CentimeterPlusInch() {
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength result = cm.add(inch);

        assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS), result);
    }
    @Test
    void testAddition_WithZero() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength zero = new QuantityLength(0.0, LengthUnit.INCH);

        QuantityLength result = q1.add(zero);

        assertEquals(new QuantityLength(5.0, LengthUnit.FEET), result);
    }
    @Test
    void testAddition_NegativeValues() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2);

        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }
    @Test
    void testAddition_NullSecondOperand() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null));
    }
    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.FEET);

        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }
    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.INCH);

        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
    }
    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(new QuantityLength(0.6666666667, LengthUnit.YARDS), result);
    }
    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);

        assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS), result);
    }
    @Test
    void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(q2, null));
    }
    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength r1 = q1.add(q2, LengthUnit.YARDS);
        QuantityLength r2 = q2.add(q1, LengthUnit.YARDS);

        assertEquals(r1, r2);
    }
}