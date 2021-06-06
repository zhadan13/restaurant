package com.example.restaurant.db.dao.impl;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.AuthorizationTokenDAO;
import com.example.restaurant.db.dao.DAO;
import com.example.restaurant.model.AuthorizationToken;
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
 * AuthorizationToken Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see AuthorizationTokenDAO
 * @see DAO
 * @see AuthorizationToken
 */

public class AuthorizationTokenDAOImpl implements AuthorizationTokenDAO {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationTokenDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static AuthorizationTokenDAOImpl INSTANCE;

    /**
     * Constructs an <b>AuthorizationTokenDAOImpl</b>.
     */
    private AuthorizationTokenDAOImpl() {

    }

    /**
     * Returns already created instance of <b>AuthorizationTokenDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link AuthorizationTokenDAOImpl} instance
     */
    public static AuthorizationTokenDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthorizationTokenDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthorizationTokenDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorizationToken> save(AuthorizationToken authorizationToken) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_AUTHORIZATION_TOKEN, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, authorizationToken.getUserId());
            preparedStatement.setString(2, authorizationToken.getToken());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                authorizationToken.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save authorization token", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(authorizationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHORIZATION_TOKEN_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete authorization token", e);
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
    public boolean update(AuthorizationToken authorizationToken) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHORIZATION_TOKEN)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, authorizationToken.getToken());
            preparedStatement.setLong(2, authorizationToken.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update authorization token", e);
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
    public Optional<AuthorizationToken> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        AuthorizationToken authorizationToken = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHORIZATION_TOKEN_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                authorizationToken = createToken(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get authorization token by id", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(authorizationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorizationToken> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<AuthorizationToken> authorizationTokens = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AUTHORIZATION_TOKENS)) {
            connection.setAutoCommit(true);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AuthorizationToken authorizationToken = createToken(resultSet);
                authorizationTokens.add(authorizationToken);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all authorization tokens", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return authorizationTokens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorizationToken> getByUserId(final Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        AuthorizationToken authorizationToken = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHORIZATION_TOKEN_BY_USER_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                authorizationToken = createToken(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get authorization token by user id", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(authorizationToken);
    }

    /**
     * Method mapping <b>ResultSet</b> to <b>AuthorizationToken</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link AuthorizationToken}
     * @throws SQLException {@inheritDoc}
     */
    private AuthorizationToken createToken(final ResultSet resultSet) throws SQLException {
        return AuthorizationToken.createToken(resultSet.getLong("id"), resultSet.getLong("user_id"),
                resultSet.getString("token"));
    }
}
