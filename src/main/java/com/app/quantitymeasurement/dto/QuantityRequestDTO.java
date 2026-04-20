package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityRequestDTO {

    @NotNull(message = "Value1 must not be null")
    private Double value1;

    @NotBlank(message = "Unit1 must not be blank")
    private String unit1;

    // value2/unit2 are optional — used for compare/add, absent for convert
    private Double value2;
    private String unit2;

    // target unit for convert endpoint
    private String targetUnit;
}
