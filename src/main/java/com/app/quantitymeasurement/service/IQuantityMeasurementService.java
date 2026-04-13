package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {
    double add(QuantityDTO a, QuantityDTO b);
    boolean compare(QuantityDTO a, QuantityDTO b);
}