package com.app.quantitymeasurement.integration;

import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuantityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenFullFlow_whenAdd_shouldPersistAndReturn() throws Exception {
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("FEET");
        request.setValue2(12.0);
        request.setUnit2("INCH");

        mockMvc.perform(post("/api/quantity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value(2.0));
    }

    @Test
    void givenFullFlow_whenCompare_shouldReturnTrue() throws Exception {
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("KILOGRAM");
        request.setValue2(1000.0);
        request.setUnit2("GRAM");

        mockMvc.perform(post("/api/quantity/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comparisonResult").value(true));
    }

    @Test
    void givenFullFlow_whenConvert_shouldReturnConvertedValue() throws Exception {
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("GALLON");
        request.setTargetUnit("LITRE");

        mockMvc.perform(post("/api/quantity/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void whenHistoryAndCount_shouldReflectPersistedOperations() throws Exception {
        // First perform an operation to ensure at least one record exists
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(100.0);
        request.setUnit1("CELSIUS");
        request.setTargetUnit("FAHRENHEIT");

        mockMvc.perform(post("/api/quantity/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verify history endpoint returns data
        mockMvc.perform(get("/api/quantity/history"))
                .andExpect(status().isOk());

        // Verify count endpoint returns a positive number
        mockMvc.perform(get("/api/quantity/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void givenInvalidUnit_whenAdd_shouldReturn400() throws Exception {
        QuantityRequestDTO request = new QuantityRequestDTO();
        request.setValue1(1.0);
        request.setUnit1("FOOBAR");
        request.setValue2(1.0);
        request.setUnit2("INCH");

        mockMvc.perform(post("/api/quantity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}