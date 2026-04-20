package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import java.util.*;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;
    private List<QuantityMeasurementEntity> storage = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null)
            instance = new QuantityMeasurementCacheRepository();
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        storage.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return storage;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        // Mock method for cache
        return new ArrayList<>();
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        // Mock method for cache
        return new ArrayList<>();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public int getTotalCount() {
        return storage.size();
    }

    @Override
    public String getPoolStatistics() {
        return "Cache Repository doesn't have a pool";
    }

    @Override
    public void releaseResources() {
        storage.clear();
    }
}