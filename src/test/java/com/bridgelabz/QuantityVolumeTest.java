package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityVolumeTest {

    private static final double EPSILON = 1e-6;

    // ----------------------
    // Equality Tests
    // ----------------------

    @Test
    void testEquality_LitreToLitre_SameValue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(v1, v2);
    }

    @Test
    void testEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> ml =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(litre, ml);
    }

    @Test
    void testEquality_GallonToLitre() {
        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> litre =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        assertEquals(gallon, litre);
    }

    // ----------------------
    // Conversion Tests
    // ----------------------

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> result =
                gallon.convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    // ----------------------
    // Addition Tests
    // ----------------------

    @Test
    void testAddition_LitrePlusMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> ml =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = litre.add(ml);

        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
    }

    @Test
    void testAddition_WithExplicitTargetUnit() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> result =
                litre.add(gallon, VolumeUnit.MILLILITRE);

        assertEquals(4785.41, result.getValue(), EPSILON);
    }

    // ----------------------
    // Cross Category Test
    // ----------------------

    @Test
    void testVolumeNotEqualToLength() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(volume, length);
    }
}