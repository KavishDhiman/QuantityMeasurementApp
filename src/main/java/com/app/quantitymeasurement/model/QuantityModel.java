package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.interfaces.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
    public double value;
    public U unit;
}