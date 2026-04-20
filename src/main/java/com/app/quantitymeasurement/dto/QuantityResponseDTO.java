package com.app.quantitymeasurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantityResponseDTO {

    private boolean success;
    private String operation;
    private Double result;
    private Boolean comparisonResult;
    private String message;
}
