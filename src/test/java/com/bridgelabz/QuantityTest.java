package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 1e-4;

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCH);

        assertEquals(q1, q2);
    }

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> w1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(w1, w2);
    }

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                q.convertTo(LengthUnit.INCH);

        assertEquals(12.0, inches.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion() {
        Quantity<WeightUnit> w =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> grams =
                w.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, grams.getValue(), EPSILON);
    }

    @Test
    void testAddition_Length() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                q1.add(q2, LengthUnit.FEET);

        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_Weight() {
        Quantity<WeightUnit> w1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                w1.add(w2, WeightUnit.KILOGRAM);

        assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testCrossCategoryPrevention() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }
}