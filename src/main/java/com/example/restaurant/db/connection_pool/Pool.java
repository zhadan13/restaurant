package com.example.restaurant.db.connection_pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Pool {
    private static final Logger LOGGER = LogManager.getLogger(Pool.class);

    private static Pool INSTANCE;
    public final Map<ConnectionImpl, Boolean> connections;
    private static final int WAIT_TIME = 1000;
    private static final String PROPERTIES_PATH = "/connection-pool.properties";
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
                LOGGER.warn("Can't parse number of connection pool, set to default", e);
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

    private ConnectionImpl createConnection() throws SQLException {
        return new ConnectionImpl(DriverManager.getConnection(URL, USER, PASSWORD), this);
    }

    public ConnectionImpl getConnection() {
        ConnectionImpl connection = null;
        for (Map.Entry<ConnectionImpl, Boolean> entry : connections.entrySet()) {
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
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("Trying to get connection again");
            return getConnection();
        } else {
            return connection;
        }
    }

    public void releaseConnection(final ConnectionImpl connectionImpl) {
        connections.put(connectionImpl, true);
    }

    public void closeAllConnections() throws SQLException {
        for (Map.Entry<ConnectionImpl, Boolean> entry : connections.entrySet()) {
            synchronized (this) {
                ConnectionImpl connection = entry.getKey();
                connection.forceClose();
            }
        }
        connections.clear();
    }

    public void reloadConnectionPool() throws SQLException {
        closeAllConnections();
        initConnections();
    }

    public void closeResources(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close statement", e);
            }
        }
    }

    public void closeResources(final ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close resultSet", e);
            }
        }
    }
}
