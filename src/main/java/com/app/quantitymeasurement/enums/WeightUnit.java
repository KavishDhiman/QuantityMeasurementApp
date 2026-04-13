package com.app.quantitymeasurement.enums;

import com.app.quantitymeasurement.interfaces.IMeasurable;
import com.app.quantitymeasurement.interfaces.SupportsArithmetic;

public enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factorToKilogram;
    WeightUnit(double factorToKilogram){
        this.factorToKilogram = factorToKilogram;
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    @Override
    // Converts value in this unit to base unit(Kilogram)
    public double toBaseUnit(double value){
        return value * factorToKilogram;
    }

    @Override
    public String getMeasurementType() {
        return "WEIGHT";
    }

    // Converts base unit to this unit
    public double convertFromBaseUnit(double value){
        return value / factorToKilogram;
    }


    public double getConversionFactor() {
        return factorToKilogram;
    }

    @Override
    public String getUnitName(){
        return name();
    }
}
