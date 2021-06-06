package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.Role;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.DAO;
import com.example.restaurant.db.dao.PasswordSaltDAO;
import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.model.PasswordSalt;
import com.example.restaurant.model.User;
import com.example.restaurant.util.PasswordEncryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.restaurant.constants.SQLQuery.*;
import static com.example.restaurant.constants.Util.*;

/**
 * User Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see UserDAO
 * @see DAO
 * @see User
 */

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static UserDAOImpl INSTANCE;

    /**
     * Constructs an <b>UserDAOImpl</b>.
     */
    private UserDAOImpl() {

    }

    /**
     * Returns already created instance of <b>UserDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link UserDAOImpl} instance
     */
    public static UserDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> save(User user) {
        final String salt = PasswordEncryptor.getSalt(SALT_DEFAULT_LENGTH);
        final String securePassword = PasswordEncryptor.generateSecurePassword(user.getPassword(), salt);
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, securePassword);
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setBoolean(6, user.getAuthorized());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                user.setId(id);

                // Saving passwordSalt for user in current transaction
                PasswordSaltDAO passwordSaltDAO = PasswordSaltDAOImpl.getInstance();
                Optional<PasswordSalt> optionalPasswordSalt = passwordSaltDAO.save(PasswordSalt.createSalt(salt, user.getId()), connection);
                optionalPasswordSalt.orElseThrow(() -> new SQLException("Can't save password salt in transaction for save user"));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Can't rollback", ex);
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                LOGGER.error("Can't set autocommit for connection to TRUE", ex);
            }
            LOGGER.error("Can't save user", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete user", e);
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
    public boolean update(User user) {
        final String salt = PasswordEncryptor.getSalt(SALT_DEFAULT_LENGTH);
        final String securePassword = PasswordEncryptor.generateSecurePassword(user.getPassword(), salt);
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, securePassword);
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();

            // Updating passwordSalt for user in current transaction
            PasswordSaltDAO passwordSaltDAO = PasswordSaltDAOImpl.getInstance();
            boolean result = passwordSaltDAO.update(PasswordSalt.createSalt(salt, user.getId()), connection);
            if (!result) {
                throw new SQLException("Can't update password salt in transaction for update user");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Can't rollback", ex);
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                LOGGER.error("Can't set autocommit for connection to TRUE", ex);
            }
            LOGGER.error("Can't update user", e);
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
    public Optional<User> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get user by id", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {
            connection.setAutoCommit(true);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all users", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateInfo(User user) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_INFO_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update user info", e);
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
    public boolean updateAuthorizationStatus(Long id, Boolean status) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_AUTHORIZATION_STATUS_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update user authorization status", e);
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
    public Optional<User> registration(User user) {
        if (!checkIfUserExistsByUniqueParameters(user)) {
            return save(user);
        }
        LOGGER.info("User with current email or phone already exists");
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> authorization(String email, String password) {
        Optional<User> optionalUser = getByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            PasswordSaltDAO passwordSaltDAO = PasswordSaltDAOImpl.getInstance();
            Optional<PasswordSalt> optionalPasswordSalt = passwordSaltDAO.getByUserId(user.getId());
            if (optionalPasswordSalt.isPresent()) {
                if (PasswordEncryptor.verifyUserPassword(password.toCharArray(), user.getPassword(), optionalPasswordSalt.get().getSalt())) {
                    return Optional.of(user);
                }
                LOGGER.info("Wrong email or password");
            }
            LOGGER.info("Can't find password salt for user password");
        }
        LOGGER.info("No such user");
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfUserExistsByUniqueParameters(User user) {
        boolean exists = false;
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_USER_EXISTS_BY_EMAIL)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            LOGGER.error("Can't check if user exists by email", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        if (!exists) {
            connection = POOL.getConnection();
            resultSet = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_USER_EXISTS_BY_PHONE_NUMBER)) {
                preparedStatement.setString(1, user.getPhoneNumber());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    exists = true;
                }
            } catch (SQLException e) {
                LOGGER.error("Can't check if user exists by phone number", e);
            } finally {
                POOL.closeResources(resultSet);
                POOL.releaseConnection(connection);
            }
        }
        return exists;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfUserExistsByEmailAndPassword(String email, String password) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_USER_EXISTS_BY_EMAIL_AND_PASSWORD)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Can't check if user exists by email and password", e);
            return false;
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getByEmail(String email) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get user by email", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    /**
     * Method mapping <b>ResultSet</b> to <b>User</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link User}
     * @throws SQLException {@inheritDoc}
     */
    private User createUser(final ResultSet resultSet) throws SQLException {
        return User.createUser(resultSet.getLong("id"), resultSet.getString("email"),
                resultSet.getString("password").toCharArray(), resultSet.getString("phone_number"),
                resultSet.getString("name"), "USER".equalsIgnoreCase(resultSet.getString("role"))
                        ? Role.USER : Role.MANAGER, resultSet.getBoolean("authorized"));
    }
}
