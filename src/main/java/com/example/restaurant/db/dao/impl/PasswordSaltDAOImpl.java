package com.example.restaurant.db.dao.impl;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.DAO;
import com.example.restaurant.db.dao.PasswordSaltDAO;
import com.example.restaurant.model.PasswordSalt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.restaurant.constants.SQLQuery.*;

/**
 * PasswordSalt Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see PasswordSaltDAO
 * @see DAO
 * @see PasswordSalt
 */

public class PasswordSaltDAOImpl implements PasswordSaltDAO {
    private static final Logger LOGGER = LogManager.getLogger(PasswordSaltDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static PasswordSaltDAOImpl INSTANCE;

    /**
     * Constructs an <b>PasswordSaltDAOImpl</b>.
     */
    private PasswordSaltDAOImpl() {

    }

    /**
     * Returns already created instance of <b>PasswordSaltDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link PasswordSaltDAOImpl} instance
     */
    public static PasswordSaltDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (PasswordSaltDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PasswordSaltDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordSalt> save(PasswordSalt passwordSalt) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PASSWORD_SALT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, passwordSalt.getSalt());
            preparedStatement.setLong(2, passwordSalt.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                passwordSalt.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save password salt", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(passwordSalt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PASSWORD_SALT_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete password salt", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(PasswordSalt passwordSalt) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SALT)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, passwordSalt.getSalt());
            preparedStatement.setLong(2, passwordSalt.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update password salt", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordSalt> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        PasswordSalt passwordSalt = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PASSWORD_SALT_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                passwordSalt = createSalt(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get password salt by id", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(passwordSalt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PasswordSalt> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<PasswordSalt> passwordSalts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PASSWORD_SALTS)) {
            connection.setAutoCommit(true);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PasswordSalt passwordSalt = createSalt(resultSet);
                passwordSalts.add(passwordSalt);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all password salts", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return passwordSalts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordSalt> save(PasswordSalt passwordSalt, ConnectionImpl connection) {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PASSWORD_SALT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, passwordSalt.getSalt());
            preparedStatement.setLong(2, passwordSalt.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                passwordSalt.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save password salt", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(passwordSalt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(PasswordSalt passwordSalt, ConnectionImpl connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SALT)) {
            preparedStatement.setString(1, passwordSalt.getSalt());
            preparedStatement.setLong(2, passwordSalt.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update password salt", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PasswordSalt> getByUserId(final Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        PasswordSalt passwordSalt = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PASSWORD_SALT_BY_USER_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                passwordSalt = createSalt(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get password salt by user id", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(passwordSalt);
    }

    /**
     * Method mapping <b>ResultSet</b> to <b>PasswordSalt</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link PasswordSalt}
     * @throws SQLException {@inheritDoc}
     */
    private PasswordSalt createSalt(final ResultSet resultSet) throws SQLException {
        return PasswordSalt.createSalt(resultSet.getLong("id"), resultSet.getString("salt"),
                resultSet.getLong("user_id"));
    }
}
