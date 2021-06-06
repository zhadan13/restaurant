package com.example.restaurant.service.impl;

import com.example.restaurant.constants.Util;
import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.db.dao.impl.UserDAOImpl;
import com.example.restaurant.model.AuthorizationToken;
import com.example.restaurant.model.User;
import com.example.restaurant.service.AuthorizationTokenService;
import com.example.restaurant.service.UserService;
import com.example.restaurant.util.AuthorizationTokenGenerator;
import com.example.restaurant.util.SendMail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.example.restaurant.util.UserValidator.*;

/**
 * User Service implementation.
 *
 * @author Zhadan Artem
 * @see User
 * @see UserService
 */

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    /**
     * Singleton instance.
     */
    private static UserServiceImpl INSTANCE;

    /**
     * Constructs an <b>UserServiceImpl</b>.
     */
    private UserServiceImpl() {

    }

    /**
     * Returns already created instance of <b>UserServiceImpl</b>, or creates new and then returns.
     *
     * @return {@link UserServiceImpl} instance
     */
    public static UserServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> registration(User user) {
        if (validation(user)) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            Optional<User> optionalUser = userDAO.registration(user);
            if (optionalUser.isPresent()) {
                final String token = AuthorizationTokenGenerator.generateToken(Util.AUTHORIZATION_TOKEN_DEFAULT_LENGTH);
                AuthorizationTokenService authorizationTokenService = AuthorizationTokenServiceImpl.getInstance();
                Optional<AuthorizationToken> authorizationTokenOptional = authorizationTokenService.saveToken(AuthorizationToken.createToken(token, user.getId()));
                if (authorizationTokenOptional.isPresent()) {
                    LOGGER.info("Service: User successfully registered. User id: " + user.getId());
                    SendMail.sendVerificationMail(user, token);
                    return Optional.of(user);
                } else {
                    LOGGER.error("Service: User registered, but authorization token not. User id: " + user.getId() + ". Token: " + token);
                }
            } else {
                LOGGER.error("Service: Can't register user");
            }
        } else {
            LOGGER.error("Service: Not valid data for registration");
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> authorization(String email, String password) {
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);
        if (isEmailValid && isPasswordValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            Optional<User> optionalUser = userDAO.authorization(email, password);
            if (optionalUser.isPresent()) {
                LOGGER.info("Service: User successfully authorized. User id: " + optionalUser.get().getId());
            } else {
                LOGGER.error("Service: Can't authorize user");
            }
            return optionalUser;
        }
        LOGGER.error("Service: Not valid data for authorization");
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validation(User user) {
        return validateUser(user.getEmail(), String.valueOf(user.getPassword()), user.getPhoneNumber(), user.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUser(User user) {
        if (validation(user)) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            boolean result = userDAO.update(user);
            if (result) {
                LOGGER.info("Service: User successfully updated. User id: " + user.getId());
            } else {
                LOGGER.error("Service: Can't update user");
            }
            return result;
        }
        LOGGER.error("Service: Not valid data for update user");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUserInformation(User user) {
        boolean isEmailValid = validateEmail(user.getEmail());
        boolean isPhoneNumberValid = validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = validateName(user.getName());
        if (isEmailValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            boolean result = userDAO.updateInfo(user);
            if (result) {
                LOGGER.info("Service: User info successfully updated. User id: " + user.getId());
            } else {
                LOGGER.error("Service: Can't update user info");
            }
            return result;
        }
        LOGGER.error("Service: Not valid data for update user info");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAuthorizationStatus(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        boolean result = userDAO.updateAuthorizationStatus(id, true);
        if (result) {
            Optional<User> optionalUser = userDAO.get(id);
            optionalUser.ifPresent(SendMail::sendInvitationMail);
        } else {
            LOGGER.error("Service: Can't update authorization status for user. User id: " + id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUser(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        Optional<User> optionalUser = userDAO.get(id);
        if (optionalUser.isPresent()) {
            LOGGER.info("Service: Successfully get user by id. User id: " + id);
        } else {
            LOGGER.error("Service: Can't get user by id. User id: " + id);
        }
        return optionalUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUser(String email) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        Optional<User> optionalUser = userDAO.getByEmail(email);
        if (optionalUser.isPresent()) {
            LOGGER.info("Service: Successfully get user by email. User id: " + optionalUser.get().getId());
        } else {
            LOGGER.error("Service: Can't get user by email");
        }
        return optionalUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUsers() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUser(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        boolean result = userDAO.delete(id);
        if (result) {
            LOGGER.info("Service: Successfully removed user by id. User id: " + id);
        } else {
            LOGGER.error("Service: Can't remove user by id");
        }
        return result;
    }
}
