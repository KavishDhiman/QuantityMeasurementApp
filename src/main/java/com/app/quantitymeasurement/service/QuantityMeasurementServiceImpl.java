package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.enums.*;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.interfaces.IMeasurable;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    private IQuantityMeasurementRepository repo;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repo) {
        this.repo = repo;
        logger.info("Service initialized with repository: {}", repo.getClass().getSimpleName());
    }

    private IMeasurable getUnit(String unit) {
        try { return LengthUnit.valueOf(unit.toUpperCase()); } catch (Exception e) {}
        try { return WeightUnit.valueOf(unit.toUpperCase()); } catch (Exception e) {}
        try { return VolumeUnit.valueOf(unit.toUpperCase()); } catch (Exception e) {}
        try { return TemperatureUnit.valueOf(unit.toUpperCase()); } catch (Exception e) {}

        logger.error("Invalid unit provided: {}", unit);
        throw new QuantityMeasurementException("Invalid unit");
    }

    @Override
    public double add(QuantityDTO a, QuantityDTO b) {
        logger.info("Adding quantities: {} {} and {} {}", a.value, a.unit, b.value, b.unit);
        IMeasurable unitA = getUnit(a.unit);
        IMeasurable unitB = getUnit(b.unit);

        if (!unitA.getMeasurementType().equals(unitB.getMeasurementType())) {
            logger.error("Measurement types mismatch: {} and {}", unitA.getMeasurementType(), unitB.getMeasurementType());
            throw new QuantityMeasurementException("Different measurement types");
        }

        double result = unitA.toBaseUnit(a.value) + unitB.toBaseUnit(b.value);
        repo.save(new QuantityMeasurementEntity(result, "ADD"));
        logger.info("Addition result: {}", result);
        return result;
    }

    @Override
    public boolean compare(QuantityDTO a, QuantityDTO b) {
        logger.info("Comparing quantities: {} {} and {} {}", a.value, a.unit, b.value, b.unit);
        IMeasurable unitA = getUnit(a.unit);
        IMeasurable unitB = getUnit(b.unit);

        double valA = unitA.toBaseUnit(a.value);
        double valB = unitB.toBaseUnit(b.value);

        boolean result = Double.compare(valA, valB) == 0;
        logger.info("Comparison result: {}", result);
        return result;
    }
}