package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.enums.*;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.interfaces.IMeasurable;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized with JPA repository");
    }

    private IMeasurable getUnit(String unit) {
        try { return LengthUnit.valueOf(unit.toUpperCase()); } catch (Exception ignored) {}
        try { return WeightUnit.valueOf(unit.toUpperCase()); } catch (Exception ignored) {}
        try { return VolumeUnit.valueOf(unit.toUpperCase()); } catch (Exception ignored) {}
        try { return TemperatureUnit.valueOf(unit.toUpperCase()); } catch (Exception ignored) {}
        logger.error("Invalid unit provided: {}", unit);
        throw new QuantityMeasurementException("Invalid unit: " + unit);
    }

    @Override
    public double add(QuantityDTO a, QuantityDTO b) {
        logger.info("Adding: {} {} + {} {}", a.getValue(), a.getUnit(), b.getValue(), b.getUnit());
        IMeasurable unitA = getUnit(a.getUnit());
        IMeasurable unitB = getUnit(b.getUnit());

        if (!unitA.getMeasurementType().equals(unitB.getMeasurementType())) {
            throw new QuantityMeasurementException(
                "Cannot add different measurement types: " + unitA.getMeasurementType()
                + " and " + unitB.getMeasurementType());
        }

        double result = unitA.toBaseUnit(a.getValue()) + unitB.toBaseUnit(b.getValue());

        QuantityMeasurementEntity entity = QuantityMeasurementEntity.builder()
                .operation("ADD")
                .value1(a.getValue())
                .unit1(a.getUnit())
                .value2(b.getValue())
                .unit2(b.getUnit())
                .resultValue(result)
                .build();
        repository.save(entity);
        logger.info("Addition result: {}", result);
        return result;
    }

    @Override
    public boolean compare(QuantityDTO a, QuantityDTO b) {
        logger.info("Comparing: {} {} vs {} {}", a.getValue(), a.getUnit(), b.getValue(), b.getUnit());
        IMeasurable unitA = getUnit(a.getUnit());
        IMeasurable unitB = getUnit(b.getUnit());

        double valA = unitA.toBaseUnit(a.getValue());
        double valB = unitB.toBaseUnit(b.getValue());

        boolean result = Double.compare(valA, valB) == 0;

        QuantityMeasurementEntity entity = QuantityMeasurementEntity.builder()
                .operation("COMPARE")
                .value1(a.getValue())
                .unit1(a.getUnit())
                .value2(b.getValue())
                .unit2(b.getUnit())
                .resultValue(result ? 1.0 : 0.0)
                .build();
        repository.save(entity);
        logger.info("Comparison result: {}", result);
        return result;
    }

    @Override
    public double convert(QuantityRequestDTO request) {
        logger.info("Converting: {} {} → {}", request.getValue1(), request.getUnit1(), request.getTargetUnit());
        IMeasurable sourceUnit  = getUnit(request.getUnit1());
        IMeasurable targetUnit  = getUnit(request.getTargetUnit());

        if (!sourceUnit.getMeasurementType().equals(targetUnit.getMeasurementType())) {
            throw new QuantityMeasurementException(
                "Cannot convert between different measurement types: "
                + sourceUnit.getMeasurementType() + " and " + targetUnit.getMeasurementType());
        }

        double baseValue = sourceUnit.toBaseUnit(request.getValue1());

        // Convert back from base to target unit
        double result;
        if (targetUnit instanceof LengthUnit lu) {
            result = lu.convertFromBaseUnit(baseValue);
        } else if (targetUnit instanceof WeightUnit wu) {
            result = wu.convertFromBaseUnit(baseValue);
        } else if (targetUnit instanceof VolumeUnit vu) {
            result = vu.convertFromBaseUnit(baseValue);
        } else if (targetUnit instanceof TemperatureUnit tu) {
            result = tu.convertFromBaseUnit(baseValue);
        } else {
            throw new QuantityMeasurementException("Conversion not supported for unit: " + request.getTargetUnit());
        }

        QuantityMeasurementEntity entity = QuantityMeasurementEntity.builder()
                .operation("CONVERT")
                .value1(request.getValue1())
                .unit1(request.getUnit1())
                .unit2(request.getTargetUnit())
                .resultValue(result)
                .build();
        repository.save(entity);
        logger.info("Conversion result: {} {}", result, request.getTargetUnit());
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getHistory() {
        logger.info("Fetching operation history");
        return repository.findAll();
    }

    @Override
    public long getCount() {
        long count = repository.count();
        logger.info("Total operations count: {}", count);
        return count;
    }
}