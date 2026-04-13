package com.app.quantitymeasurement.interfaces;

public interface IMeasurable {
    String getUnitName();
    String getMeasurementType();
    double toBaseUnit(double value);
}