package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private static QuantityMeasurementDatabaseRepository instance;

    private QuantityMeasurementDatabaseRepository() {}

    public static QuantityMeasurementDatabaseRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementDatabaseRepository();
        }
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String sql = "INSERT INTO quantity_measurement_entity (resultValue, operation, isError, errorMessage) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, entity.resultValue);
                pstmt.setString(2, entity.operation);
                pstmt.setBoolean(3, entity.isError);
                pstmt.setString(4, entity.errorMessage);
                pstmt.executeUpdate();
                logger.info("Saved entity with operation: {}", entity.operation);
            }
        } catch (SQLException e) {
            logger.error("Error saving entity", e);
            throw new DatabaseException("Error saving entity", e);
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        String sql = "SELECT * FROM quantity_measurement_entity";
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all measurements", e);
            throw new DatabaseException("Error retrieving measurements", e);
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        String sql = "SELECT * FROM quantity_measurement_entity WHERE operation = ?";
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, operation);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToEntity(rs));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving measurements by operation", e);
            throw new DatabaseException("Error retrieving by operation", e);
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        // Since 'type' is not strictly in our entity structure, we can map it to 'operation' or return empty
        return new ArrayList<>(); 
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM quantity_measurement_entity";
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rows = pstmt.executeUpdate();
                logger.info("Deleted {} rows", rows);
            }
        } catch (SQLException e) {
            logger.error("Error deleting all measurements", e);
            throw new DatabaseException("Error deleting", e);
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM quantity_measurement_entity";
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting total count", e);
            throw new DatabaseException("Error counting", e);
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
        return 0;
    }

    @Override
    public String getPoolStatistics() {
        return ConnectionPool.getPoolStatistics();
    }

    @Override
    public void releaseResources() {
        ConnectionPool.closePool();
        logger.info("Database resources released.");
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        boolean isError = rs.getBoolean("isError");
        if (isError) {
            String msg = rs.getString("errorMessage");
            return new QuantityMeasurementEntity(msg);
        } else {
            double val = rs.getDouble("resultValue");
            String op = rs.getString("operation");
            return new QuantityMeasurementEntity(val, op);
        }
    }
}
