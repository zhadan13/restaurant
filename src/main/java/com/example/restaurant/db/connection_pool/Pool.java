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

/**
 * Singleton connection pool implementation based on {@link ConnectionImpl}.
 *
 * @author Zhadan Artem
 * @see ConnectionImpl
 */

public class Pool {
    private static final Logger LOGGER = LogManager.getLogger(Pool.class);

    /**
     * Singleton instance.
     */
    private static Pool INSTANCE;

    /**
     * Collection of used connections.
     */
    private final Map<ConnectionImpl, Boolean> connections;

    /**
     * Waiting time to receive a new connection if all connections already busy.
     */
    private static final int WAIT_TIME = 1000;

    /**
     * Path to configuration file for connection pool.
     */
    private static final String PROPERTIES_PATH = "/connection-pool.properties";

    /**
     * Database URL
     */
    private static String URL;

    /**
     * Database User
     */
    private static String USER;

    /**
     * Database password
     */
    private static String PASSWORD;

    /**
     * Number of connections in pool.
     */
    private static int NUMBER_OF_CONNECTIONS;

    /**
     * Default number of connections in pool.
     */
    private static final int DEFAULT_NUMBER_OF_CONNECTIONS = 30;

    /**
     * Constructs an <b>Connection Pool</b>.
     */
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

    /**
     * Returns already created instance of <b>Pool</b>, or creates new and then returns.
     *
     * @return {@link Pool} instance
     */
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

    /**
     * Init method for connections.
     * This method puts {@literal NUMBER_OF_CONNECTIONS} connections to collection.
     *
     * @throws SQLException {@inheritDoc}
     */
    private void initConnections() throws SQLException {
        for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
            connections.put(createConnection(), true);
        }
    }

    /**
     * Returns new single connection for <b>Pool</b>.
     *
     * @return {@link ConnectionImpl}
     * @throws SQLException {@inheritDoc}
     */
    private ConnectionImpl createConnection() throws SQLException {
        return new ConnectionImpl(DriverManager.getConnection(URL, USER, PASSWORD), this);
    }

    /**
     * Returns connection from <b>Pool</b>.
     *
     * @return {@link ConnectionImpl}
     */
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
                LOGGER.error(e);
            }
            LOGGER.info("Trying to get connection again");
            return getConnection();
        } else {
            return connection;
        }
    }

    /**
     * Method for releasing connection.
     *
     * @param connectionImpl connection to be released
     */
    public void releaseConnection(final ConnectionImpl connectionImpl) {
        connections.put(connectionImpl, true);
    }

    /**
     * Method for closing all connections in pool.
     *
     * @throws SQLException {@inheritDoc}
     */
    public void closeAllConnections() throws SQLException {
        for (Map.Entry<ConnectionImpl, Boolean> entry : connections.entrySet()) {
            synchronized (this) {
                ConnectionImpl connection = entry.getKey();
                connection.forceClose();
            }
        }
        connections.clear();
    }

    /**
     * Method for reloading <b>Pool</b>.
     *
     * @throws SQLException {@inheritDoc}
     */
    public void reloadConnectionPool() throws SQLException {
        closeAllConnections();
        initConnections();
    }

    /**
     * Method for closing statement.
     *
     * @param statement statement to be closed
     */
    public void closeResources(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close statement", e);
            }
        }
    }

    /**
     * Method for closing resultSet.
     *
     * @param resultSet resultSet to be closed
     */
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
