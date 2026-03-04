package com.bridgelabz;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-5;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // --------------------------------
    // Equality
    // --------------------------------


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity<?> other))
            return false;

        // prevent cross category comparison
        if (!this.unit.getClass().equals(other.unit.getClass()))
            return false;

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = ((IMeasurable) other.unit).convertToBaseUnit(other.value);

        return Math.abs(baseValue1 - baseValue2) <= EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        long normalized = Math.round(baseValue / EPSILON);
        return Objects.hash(normalized);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    // --------------------------------
    // Conversion
    // --------------------------------

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);

        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    // --------------------------------
    // Arithmetic Operations Enum
    // --------------------------------

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> a / b);

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    // --------------------------------
    // Validation
    // --------------------------------

    private void validateArithmeticOperands(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different measurement categories");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");
    }

    // --------------------------------
    // Centralized Arithmetic
    // --------------------------------

    private double performBaseArithmetic(Quantity<U> other,
                                         ArithmeticOperation operation) {

        validateArithmeticOperands(other);

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        if (operation == ArithmeticOperation.DIVIDE && baseValue2 == 0)
            throw new ArithmeticException("Division by zero");

        return operation.compute(baseValue1, baseValue2);
    }

    // --------------------------------
    // Addition
    // --------------------------------

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(result, targetUnit);
    }

    // --------------------------------
    // Subtraction
    // --------------------------------

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(result, targetUnit);
    }

    // --------------------------------
    // Division
    // --------------------------------

    public double divide(Quantity<U> other) {

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }
}