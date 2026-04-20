package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementApp {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementApp.class);

    private static IQuantityMeasurementRepository getRepository() {
        String repoType = AppConfig.getRepositoryType();
        logger.info("Configured repository type: {}", repoType);
        if ("DATABASE".equalsIgnoreCase(repoType)) {
            return QuantityMeasurementDatabaseRepository.getInstance();
        }
        return QuantityMeasurementCacheRepository.getInstance();
    }

    public static void main(String[] args) {
        logger.info("Starting Quantity Measurement App");

        IQuantityMeasurementRepository repository = getRepository();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        try {
            // Delete old measurements for clean slate
            repository.deleteAll();
            logger.info("Deleted all existing measurements.");

            QuantityDTO q1 = new QuantityDTO();
            q1.value = 1;
            q1.unit = "FEET";

            QuantityDTO q2 = new QuantityDTO();
            q2.value = 12;
            q2.unit = "INCH";

            logger.info("Equal: {}", controller.compare(q1, q2));
            logger.info("Sum: {}", controller.add(q1, q2));

            logger.info("Total measurements in repo: {}", repository.getTotalCount());
            logger.info("Pool Statistics: {}", repository.getPoolStatistics());

        } catch (Exception e) {
            logger.error("Application encountered an error", e);
        } finally {
            logger.info("Closing application resources");
            repository.releaseResources();
        }
        
        logger.info("Application finished");
    }
}