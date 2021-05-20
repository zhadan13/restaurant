package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.Role;
import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.constants.Util;
import com.example.restaurant.db.connection_pool.ConnectionPool;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.PasswordSaltDAO;
import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.model.PasswordSalt;
import com.example.restaurant.model.User;
import com.example.restaurant.util.PasswordEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl INSTANCE;
    private static final Pool POOL = Pool.getInstance();

    private UserDAOImpl() {

    }

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

    @Override
    public Optional<User> save(User user) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String salt = PasswordEncryptor.getSalt(Util.SALT_DEFAULT_LENGTH);
            String securePassword = PasswordEncryptor.generateSecurePassword(user.getPassword(), salt);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, securePassword);
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long field = resultSet.getLong(1);
                user.setId(field);
                PasswordSaltDAO passwordSaltDAO = PasswordSaltDAOImpl.getInstance();
                passwordSaltDAO.save(PasswordSalt.createSalt(salt, user.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(user);
    }

    @Override
    public boolean delete(Long id) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_USER_BY_ID)) {
            String salt = PasswordEncryptor.getSalt(Util.SALT_DEFAULT_LENGTH);
            String securePassword = PasswordEncryptor.generateSecurePassword(user.getPassword(), salt);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, securePassword);
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();

            PasswordSaltDAO passwordSaltDAO = PasswordSaltDAOImpl.getInstance();
            passwordSaltDAO.update(PasswordSalt.createSalt(salt, user.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public Optional<User> get(Long id) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_USERS)) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return users;
    }

    @Override
    public boolean updateInfo(User user) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_USER_INFO_BY_ID)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public Optional<User> registration(User user) {
        if (!checkIfUserExistsByUniqueParameters(user)) {
            return save(user);
        }
        return Optional.empty();
    }

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
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkIfUserExistsByUniqueParameters(User user) {
        boolean exists = false;
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CHECK_IF_USER_EXISTS_BY_EMAIL)) {
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        if (!exists) {
            connection = POOL.getConnection();
            resultSet = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CHECK_IF_USER_EXISTS_BY_PHONE_NUMBER)) {
                preparedStatement.setString(1, user.getPhoneNumber());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    exists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                POOL.closeResources(resultSet);
                POOL.releaseConnection(connection);
            }
        }
        return exists;
    }

    @Override
    public boolean checkIfUserExistsByEmailAndPassword(String email, String password) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CHECK_IF_USER_EXISTS_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    private User createUser(final ResultSet resultSet) throws SQLException {
        return User.createUser(resultSet.getLong("id"), resultSet.getString("email"),
                resultSet.getString("password").toCharArray(), resultSet.getString("phone_number"),
                resultSet.getString("name"), "USER".equalsIgnoreCase(resultSet.getString("role"))
                        ? Role.USER : Role.MANAGER);
    }
}
