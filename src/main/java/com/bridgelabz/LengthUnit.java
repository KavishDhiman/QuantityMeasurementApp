package com.bridgelabz;

/**
 * Enum representing supported length units.
 * Each unit stores conversion factor relative to base unit (FEET).
 */
public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0); // 1 cm = 0.393701 inches

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    /**
     * Converts given value of this unit into feet.
     */
    public double toFeet(double value) {
        return value * conversionFactorToFeet;
    }

    /**
     * Returns conversion factor relative to feet.
     */
    public double getConversionFactor() {
        return conversionFactorToFeet;
    }
}