package com.app.quantitymeasurement.enums;

import com.app.quantitymeasurement.interfaces.IMeasurable;
import com.app.quantitymeasurement.interfaces.SupportsArithmetic;

public enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factorToLitre;

    VolumeUnit(double factorToLitre){
        this.factorToLitre = factorToLitre;
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    @Override
    public double toBaseUnit(double value){
        return value * factorToLitre;
    }

    @Override
    public String getMeasurementType() {
        return "VOLUME";
    }

    public double convertFromBaseUnit(double baseValue){
        return baseValue / factorToLitre;
    }



    public double getConversionFactor(){
        return factorToLitre;
    }

    @Override
    public String getUnitName(){
        return name();
    }
}
