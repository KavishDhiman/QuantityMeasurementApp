package com.bridgelabz;

import java.util.Objects;

/**
 * Immutable value object representing a length measurement.
 * Supports equality comparison and unit-to-unit conversion.
 */
public final class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    /**
     * Constructor with validation.
     */
    public QuantityLength(double value, LengthUnit unit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    /**
     * Static conversion method.
     */
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null.");
        }

        if (source == target) {
            return value;
        }

        double valueInFeet = source.toFeet(value);
        return valueInFeet / target.getConversionFactor();
    }
    /**
     * Adds another QuantityLength to this one.
     * Result is returned in the unit of this object.
     */
    public QuantityLength add(QuantityLength other) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }

        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value in second operand.");
        }

        // Convert both to base unit (feet)
        double thisInFeet = this.toBaseUnit();
        double otherInFeet = other.toBaseUnit();

        // Add in base unit
        double sumInFeet = thisInFeet + otherInFeet;

        // Convert back to this object's unit
        double resultValue = sumInFeet / this.unit.getConversionFactor();

        return new QuantityLength(resultValue, this.unit);
    }
    /**
     * Instance conversion method.
     * Returns new immutable QuantityLength object.
     */
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * Converts internal value to base unit (feet).
     */
    private double toBaseUnit() {
        return unit.toFeet(value);
    }

    /**
     * Equality based on normalized base unit value.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() / EPSILON));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}