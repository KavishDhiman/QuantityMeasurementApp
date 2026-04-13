-- schema.sql
CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resultValue DOUBLE,
    operation VARCHAR(255),
    isError BOOLEAN,
    errorMessage VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(50) NOT NULL,
    value1 DOUBLE,
    unit1 VARCHAR(50),
    value2 DOUBLE,
    unit2 VARCHAR(50),
    result DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_operation ON quantity_measurement_entity(operation);
