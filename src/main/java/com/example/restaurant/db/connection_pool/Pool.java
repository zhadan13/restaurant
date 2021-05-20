package com.example.restaurant.db.connection_pool;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Pool {
    private static Pool INSTANCE;
    public final Map<ConnectionPool, Boolean> connections;
    private static final int WAIT_TIME = 1000;
    private static final String PROPERTIES_PATH = "/pool-configuration.properties";
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static int NUMBER_OF_CONNECTIONS;
    private static final int DEFAULT_NUMBER_OF_CONNECTIONS = 30;

    private Pool() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(PROPERTIES_PATH));
            Class.forName(properties.getProperty("db.driver"));
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
            try {
                NUMBER_OF_CONNECTIONS = Integer.parseInt(properties.getProperty("db.pool_size"));
            } catch (NumberFormatException e) {
                NUMBER_OF_CONNECTIONS = DEFAULT_NUMBER_OF_CONNECTIONS;
                e.printStackTrace();
            }
            connections = new HashMap<>(NUMBER_OF_CONNECTIONS);
            initConnections();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pool getInstance() {
        if (INSTANCE == null) {
            synchronized (Pool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Pool();
                }
            }
        }
        return INSTANCE;
    }

    private void initConnections() throws SQLException {
        for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
            connections.put(createConnection(), true);
        }
    }

    private ConnectionPool createConnection() throws SQLException {
        return new ConnectionPool(DriverManager.getConnection(URL, USER, PASSWORD), this);
    }

    public ConnectionPool getConnection() {
        ConnectionPool connection = null;
        for (Map.Entry<ConnectionPool, Boolean> entry : connections.entrySet()) {
            if (entry.getValue()) {
                synchronized (this) {
                    if (entry.getValue()) {
                        connection = entry.getKey();
                        connections.put(connection, false);
                        break;
                    }
                }
            }
        }
        if (connection == null) {
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getConnection();
        } else {
            return connection;
        }
    }

    public void closeAllConnections() throws SQLException {
        for (Map.Entry<ConnectionPool, Boolean> entry : connections.entrySet()) {
            synchronized (this) {
                ConnectionPool connection = entry.getKey();
                connection.closeConnection();
            }
        }
        connections.clear();
    }

    public void reloadConnectionPool() throws SQLException {
        closeAllConnections();
        initConnections();
    }

    public void releaseConnection(final ConnectionPool connectionPool) {
        connections.put(connectionPool, true);
    }

    public void closeResources(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResources(final ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}