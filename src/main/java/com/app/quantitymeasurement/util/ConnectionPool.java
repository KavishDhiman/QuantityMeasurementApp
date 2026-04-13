package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.DatabaseException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool {
    private static final Properties properties = new Properties();
    private static String url;
    private static String user;
    private static String password;
    private static int maxConnections;
    private static int minIdle;

    private static List<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();

    static {
        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            } else {
                properties.load(input);
            }

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            if (password == null) password = "";
            maxConnections = Integer.parseInt(properties.getProperty("db.pool.maxConnections", "10"));
            minIdle = Integer.parseInt(properties.getProperty("db.pool.minIdle", "2"));

            if (url != null && url.startsWith("jdbc:h2")) {
                Class.forName("org.h2.Driver");
            }

            connectionPool = new ArrayList<>(maxConnections);

            for (int i = 0; i < minIdle; i++) {
                connectionPool.add(createConnection(url, user, password));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to initialize connection pool", e);
        }
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxConnections) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new DatabaseException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        
        try {
            if (!connection.isValid(1)) {
                connection = createConnection(url, user, password);
            }
        } catch (AbstractMethodError e) {
            // some older drivers don't implement isValid
        }
        
        usedConnections.add(connection);
        return connection;
    }

    public static synchronized boolean releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            connectionPool.add(connection);
            return true;
        }
        return false;
    }

    public static String getPoolStatistics() {
        return String.format("Active: %d, Idle: %d, Total: %d, Max: %d", 
            usedConnections.size(), connectionPool.size(), 
            usedConnections.size() + connectionPool.size(), maxConnections);
    }

    public static synchronized void closePool() {
        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionPool.clear();

        for (Connection c : usedConnections) {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        usedConnections.clear();
    }
}
