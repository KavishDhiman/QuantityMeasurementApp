package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementService {
    double add(QuantityDTO a, QuantityDTO b);
    boolean compare(QuantityDTO a, QuantityDTO b);
    double convert(QuantityRequestDTO request);
    List<QuantityMeasurementEntity> getHistory();
    long getCount();
}