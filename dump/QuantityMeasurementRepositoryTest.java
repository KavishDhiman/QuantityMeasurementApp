package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementRepositoryTest {

    @Test
    public void givenEntity_whenSaved_shouldBeStored() {
        IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(10, "ADD");

        repo.save(entity);

        assertTrue(repo.getAllMeasurements().size() > 0);
    }
}