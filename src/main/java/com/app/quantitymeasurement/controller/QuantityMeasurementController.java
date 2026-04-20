package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.dto.QuantityResponseDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quantity")
@Tag(name = "Quantity Measurement API", description = "REST endpoints for quantity comparison, conversion and addition")
public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);
    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialized");
    }

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities", description = "Returns true if both quantities are equal in base unit")
    public ResponseEntity<QuantityResponseDTO> compare(@Valid @RequestBody QuantityRequestDTO request) {
        logger.info("POST /api/quantity/compare");
        QuantityDTO a = new QuantityDTO(request.getValue1(), request.getUnit1());
        QuantityDTO b = new QuantityDTO(request.getValue2(), request.getUnit2());
        boolean result = service.compare(a, b);
        return ResponseEntity.ok(QuantityResponseDTO.builder()
                .success(true)
                .operation("COMPARE")
                .comparisonResult(result)
                .message(result ? "Quantities are equal" : "Quantities are not equal")
                .build());
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to a target unit")
    public ResponseEntity<QuantityResponseDTO> convert(@Valid @RequestBody QuantityRequestDTO request) {
        logger.info("POST /api/quantity/convert");
        double result = service.convert(request);
        return ResponseEntity.ok(QuantityResponseDTO.builder()
                .success(true)
                .operation("CONVERT")
                .result(result)
                .message("Converted " + request.getValue1() + " " + request.getUnit1()
                        + " to " + result + " " + request.getTargetUnit())
                .build());
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities of the same type")
    public ResponseEntity<QuantityResponseDTO> add(@Valid @RequestBody QuantityRequestDTO request) {
        logger.info("POST /api/quantity/add");
        QuantityDTO a = new QuantityDTO(request.getValue1(), request.getUnit1());
        QuantityDTO b = new QuantityDTO(request.getValue2(), request.getUnit2());
        double result = service.add(a, b);
        return ResponseEntity.ok(QuantityResponseDTO.builder()
                .success(true)
                .operation("ADD")
                .result(result)
                .message("Sum in base unit: " + result)
                .build());
    }

    @GetMapping("/history")
    @Operation(summary = "Get all operation history")
    public ResponseEntity<List<QuantityMeasurementEntity>> history() {
        logger.info("GET /api/quantity/history");
        return ResponseEntity.ok(service.getHistory());
    }

    @GetMapping("/count")
    @Operation(summary = "Get total number of operations performed")
    public ResponseEntity<QuantityResponseDTO> count() {
        logger.info("GET /api/quantity/count");
        long count = service.getCount();
        return ResponseEntity.ok(QuantityResponseDTO.builder()
                .success(true)
                .operation("COUNT")
                .result((double) count)
                .message("Total operations: " + count)
                .build());
    }
}