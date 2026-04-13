package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {
    
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);
    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        logger.info("Controller initialized with service: {}", service.getClass().getSimpleName());
    }

    public double add(QuantityDTO a, QuantityDTO b) {
        logger.info("Controller add request received");
        return service.add(a, b);
    }

    public boolean compare(QuantityDTO a, QuantityDTO b) {
        logger.info("Controller compare request received");
        return service.compare(a, b);
    }
}