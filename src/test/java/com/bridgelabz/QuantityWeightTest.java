package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityWeightTest {

    private static final double EPSILON = 1e-4;

    // ----------------------
    // Equality Tests
    // ----------------------

    @Test
    void testEquality_KilogramToKilogram() {
        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(q1, q2);
    }

    @Test
    void testEquality_KilogramToGram() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(kg, gram);
    }

    @Test
    void testEquality_KilogramToPound() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> pound =
                new Quantity<>(2.20462, WeightUnit.POUND);

        assertEquals(kg, pound);
    }

    @Test
    void testEquality_DifferentValue() {
        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(2.0, WeightUnit.KILOGRAM);

        assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_NullComparison() {
        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(null, q);
    }

    // ----------------------
    // Conversion Tests
    // ----------------------

    @Test
    void testConversion_KilogramToGram() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, gram.getValue(), EPSILON);
    }

    @Test
    void testConversion_PoundToKilogram() {
        Quantity<WeightUnit> pound =
                new Quantity<>(2.20462, WeightUnit.POUND);

        Quantity<WeightUnit> kg =
                pound.convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.0, kg.getValue(), EPSILON);
    }

    @Test
    void testConversion_SameUnit() {
        Quantity<WeightUnit> kg =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                kg.convertTo(WeightUnit.KILOGRAM);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        Quantity<WeightUnit> kg =
                new Quantity<>(0.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(0.0, gram.getValue(), EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        Quantity<WeightUnit> kg =
                new Quantity<>(-1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(-1000.0, gram.getValue(), EPSILON);
    }

    // ----------------------
    // Addition Tests
    // ----------------------

    @Test
    void testAddition_SameUnit() {
        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(2.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                q1.add(q2);

        assertEquals(new Quantity<>(3.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testAddition_CrossUnit_KgPlusGram() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                kg.add(gram);

        assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testAddition_ExplicitTargetUnit() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                kg.add(gram, WeightUnit.GRAM);

        assertEquals(new Quantity<>(2000.0, WeightUnit.GRAM), result);
    }

    @Test
    void testAddition_WithZero() {
        Quantity<WeightUnit> kg =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> zero =
                new Quantity<>(0.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                kg.add(zero);

        assertEquals(new Quantity<>(5.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testCrossCategoryComparison() {
        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(weight, length);
    }
}