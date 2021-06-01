package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
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

public class PasswordSaltDAOImpl implements PasswordSaltDAO {
    private static final Logger LOGGER = LogManager.getLogger(PasswordSaltDAOImpl.class);

    private static PasswordSaltDAOImpl INSTANCE;

    private PasswordSaltDAOImpl() {

    }

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

    @Override
    public Optional<PasswordSalt> save(PasswordSalt passwordSalt) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_PASSWORD_SALT, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_PASSWORD_SALT_BY_ID)) {
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

    @Override
    public boolean update(PasswordSalt passwordSalt) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_PASSWORD_SALT)) {
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

    @Override
    public Optional<PasswordSalt> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        PasswordSalt passwordSalt = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_PASSWORD_SALT_BY_ID)) {
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

    @Override
    public List<PasswordSalt> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<PasswordSalt> passwordSalts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_PASSWORD_SALTS)) {
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

    @Override
    public Optional<PasswordSalt> save(PasswordSalt passwordSalt, ConnectionImpl connection) {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_PASSWORD_SALT, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public boolean update(PasswordSalt passwordSalt, ConnectionImpl connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_PASSWORD_SALT)) {
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

    @Override
    public Optional<PasswordSalt> getByUserId(final Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        PasswordSalt passwordSalt = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_PASSWORD_SALT_BY_USER_ID)) {
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

    private PasswordSalt createSalt(final ResultSet resultSet) throws SQLException {
        return PasswordSalt.createSalt(resultSet.getLong("id"), resultSet.getString("salt"),
                resultSet.getLong("user_id"));
    }
}
