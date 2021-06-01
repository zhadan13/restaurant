package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.AuthorizationTokenDAO;
import com.example.restaurant.model.AuthorizationToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorizationTokenDAOImpl implements AuthorizationTokenDAO {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationTokenDAOImpl.class);

    private static AuthorizationTokenDAOImpl INSTANCE;

    private AuthorizationTokenDAOImpl() {

    }

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

    @Override
    public Optional<AuthorizationToken> save(AuthorizationToken authorizationToken) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_AUTHORIZATION_TOKEN, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_AUTHORIZATION_TOKEN_BY_ID)) {
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

    @Override
    public boolean update(AuthorizationToken authorizationToken) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_AUTHORIZATION_TOKEN)) {
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

    @Override
    public Optional<AuthorizationToken> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        AuthorizationToken authorizationToken = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_AUTHORIZATION_TOKEN_BY_ID)) {
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

    @Override
    public List<AuthorizationToken> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<AuthorizationToken> authorizationTokens = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_AUTHORIZATION_TOKENS)) {
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

    @Override
    public Optional<AuthorizationToken> getByUserId(final Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        AuthorizationToken authorizationToken = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_AUTHORIZATION_TOKEN_BY_USER_ID)) {
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

    private AuthorizationToken createToken(final ResultSet resultSet) throws SQLException {
        return AuthorizationToken.createToken(resultSet.getLong("id"), resultSet.getLong("user_id"),
                resultSet.getString("token"));
    }
}
