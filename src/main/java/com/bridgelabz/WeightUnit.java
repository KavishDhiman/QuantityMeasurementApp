package com.bridgelabz;

public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor; // relative to KILOGRAM (base unit)

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert to base unit (KILOGRAM)
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    // Convert from base unit (KILOGRAM)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}