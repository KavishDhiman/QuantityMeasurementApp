package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String type);
    void deleteAll();
    int getTotalCount();
    String getPoolStatistics();
    void releaseResources();
}