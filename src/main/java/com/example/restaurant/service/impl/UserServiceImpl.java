package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.db.dao.impl.UserDAOImpl;
import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.util.SendMail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.example.restaurant.util.UserValidator.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static UserServiceImpl INSTANCE;

    private UserServiceImpl() {

    }

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

    @Override
    public Optional<User> registration(User user) {
        if (validation((user))) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.registration(user);
        }
        LOGGER.info("Not valid data for registration");
        return Optional.empty();
    }

    @Override
    public Optional<User> authorization(String email, String password) {
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);
        if (isEmailValid && isPasswordValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.authorization(email, password);
        }
        LOGGER.info("Not valid data for authorization");
        return Optional.empty();
    }

    @Override
    public boolean validation(User user) {
        boolean isEmailValid = validateEmail(user.getEmail());
        boolean isPasswordValid = validatePassword(String.valueOf(user.getPassword()));
        boolean isPhoneNumberValid = validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = validateName(user.getName());
        return isEmailValid && isPasswordValid && isPhoneNumberValid && isNameValid;
    }

    @Override
    public boolean updateUser(User user) {
        boolean isEmailValid = validateEmail(user.getEmail());
        boolean isPasswordValid = validatePassword(String.valueOf(user.getPassword()));
        boolean isPhoneNumberValid = validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = validateName(user.getName());
        if (isEmailValid && isPasswordValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.update(user);
        }
        LOGGER.info("Not valid data for update user");
        return false;
    }

    @Override
    public boolean updateUserInformation(User user) {
        boolean isEmailValid = validateEmail(user.getEmail());
        boolean isPhoneNumberValid = validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = validateName(user.getName());
        if (isEmailValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.updateInfo(user);
        }
        LOGGER.info("Not valid data for update user info");
        return false;
    }

    @Override
    public boolean updateAuthorizationStatus(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        boolean result = userDAO.updateAuthorizationStatus(id, true);
        if (result) {
            Optional<User> optionalUser = userDAO.get(id);
            optionalUser.ifPresent(user -> SendMail.sendInvitationMail(user.getEmail(), user.getName()));
        } else {
            LOGGER.warn("Can't update authorization status for user");
        }
        return result;
    }

    @Override
    public Optional<User> getUser(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.get(id);
    }

    @Override
    public Optional<User> getUser(String email) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.getByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.getAll();
    }

    @Override
    public boolean deleteUser(Long id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.delete(id);
    }
}
