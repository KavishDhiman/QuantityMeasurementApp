package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuantityMeasurementServiceTest {

    private QuantityMeasurementRepository repo;
    private IQuantityMeasurementService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(QuantityMeasurementRepository.class);
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        service = new QuantityMeasurementServiceImpl(repo);
    }

    @Test
    void given1FeetAnd12Inches_whenCompared_shouldReturnTrue() {
        QuantityDTO a = new QuantityDTO(1.0, "FEET");
        QuantityDTO b = new QuantityDTO(12.0, "INCH");
        assertTrue(service.compare(a, b));
    }

    @Test
    void given1KilogramAnd1000Grams_whenCompared_shouldReturnTrue() {
        QuantityDTO a = new QuantityDTO(1.0, "KILOGRAM");
        QuantityDTO b = new QuantityDTO(1000.0, "GRAM");
        assertTrue(service.compare(a, b));
    }

    @Test
    void given1FeetAnd1Inch_whenCompared_shouldReturnFalse() {
        QuantityDTO a = new QuantityDTO(1.0, "FEET");
        QuantityDTO b = new QuantityDTO(1.0, "INCH");
        assertFalse(service.compare(a, b));
    }

    @Test
    void given1FeetAnd12Inches_whenAdded_shouldReturn2Feet() {
        QuantityDTO a = new QuantityDTO(1.0, "FEET");
        QuantityDTO b = new QuantityDTO(12.0, "INCH");
        double result = service.add(a, b);
        assertEquals(2.0, result, 0.001);
    }

    @Test
    void givenDifferentTypes_whenAdded_shouldThrowException() {
        QuantityDTO a = new QuantityDTO(1.0, "FEET");
        QuantityDTO b = new QuantityDTO(1.0, "KILOGRAM");
        assertThrows(QuantityMeasurementException.class, () -> service.add(a, b));
    }

    @Test
    void given1FeetConvertToInch_shouldReturn12() {
        QuantityRequestDTO req = new QuantityRequestDTO();
        req.setValue1(1.0);
        req.setUnit1("FEET");
        req.setTargetUnit("INCH");
        double result = service.convert(req);
        assertEquals(12.0, result, 0.001);
    }

    @Test
    void given1GallonConvertToLitre_shouldBeCorrect() {
        QuantityRequestDTO req = new QuantityRequestDTO();
        req.setValue1(1.0);
        req.setUnit1("GALLON");
        req.setTargetUnit("LITRE");
        double result = service.convert(req);
        assertEquals(3.78541, result, 0.001);
    }

    @Test
    void givenInvalidUnit_whenCompare_shouldThrowException() {
        QuantityDTO a = new QuantityDTO(1.0, "INVALID_UNIT");
        QuantityDTO b = new QuantityDTO(1.0, "FEET");
        assertThrows(QuantityMeasurementException.class, () -> service.compare(a, b));
    }

    @Test
    void whenGetHistory_shouldReturnRepositoryResults() {
        when(repo.findAll()).thenReturn(List.of());
        List<QuantityMeasurementEntity> history = service.getHistory();
        assertNotNull(history);
        verify(repo, times(1)).findAll();
    }

    @Test
    void whenGetCount_shouldReturnRepositoryCount() {
        when(repo.count()).thenReturn(3L);
        assertEquals(3L, service.getCount());
    }
}