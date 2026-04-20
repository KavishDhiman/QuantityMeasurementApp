package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.dto.QuantityResponseDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.quantitymeasurement.config.SecurityConfig;

@WebMvcTest(QuantityMeasurementController.class)
@Import(SecurityConfig.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidRequest_whenCompare_shouldReturn200WithResult() throws Exception {
        when(service.compare(any(), any())).thenReturn(true);

        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("FEET");
        request.setValue2(12.0);
        request.setUnit2("INCH");

        mockMvc.perform(post("/api/quantity/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.comparisonResult").value(true));
    }

    @Test
    void givenValidRequest_whenAdd_shouldReturn200WithSum() throws Exception {
        when(service.add(any(), any())).thenReturn(4.0);

        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(2.0);
        request.setUnit1("FEET");
        request.setValue2(24.0);
        request.setUnit2("INCH");

        mockMvc.perform(post("/api/quantity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value(4.0));
    }

    @Test
    void givenValidRequest_whenConvert_shouldReturn200WithConvertedValue() throws Exception {
        when(service.convert(any())).thenReturn(12.0);

        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("FEET");
        request.setTargetUnit("INCH");

        mockMvc.perform(post("/api/quantity/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value(12.0));
    }

    @Test
    void whenHistory_shouldReturn200WithList() throws Exception {
        when(service.getHistory()).thenReturn(List.of());

        mockMvc.perform(get("/api/quantity/history"))
                .andExpect(status().isOk());
    }

    @Test
    void whenCount_shouldReturn200WithCount() throws Exception {
        when(service.getCount()).thenReturn(5L);

        mockMvc.perform(get("/api/quantity/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void givenMissingUnit_whenAdd_shouldReturn422() throws Exception {
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        // unit1 is intentionally missing — should fail validation

        mockMvc.perform(post("/api/quantity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }
}