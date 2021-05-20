package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.db.dao.impl.UserDAOImpl;
import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.util.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
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
        boolean isEmailValid = UserValidator.validateEmail(user.getEmail());
        boolean isPasswordValid = UserValidator.validatePassword(String.valueOf(user.getPassword()));
        boolean isPhoneNumberValid = UserValidator.validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = UserValidator.validateName(user.getName());
        if (isEmailValid && isPasswordValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.registration(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> authorization(String email, String password) {
        boolean isEmailValid = UserValidator.validateEmail(email);
        boolean isPasswordValid = UserValidator.validatePassword(password);
        if (isEmailValid && isPasswordValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.authorization(email, password);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateUser(User user) {
        boolean isEmailValid = UserValidator.validateEmail(user.getEmail());
        boolean isPasswordValid = UserValidator.validatePassword(String.valueOf(user.getPassword()));
        boolean isPhoneNumberValid = UserValidator.validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = UserValidator.validateName(user.getName());
        if (isEmailValid && isPasswordValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.update(user);
        }
        return false;
    }

    @Override
    public boolean updateUserInformation(User user) {
        boolean isEmailValid = UserValidator.validateEmail(user.getEmail());
        boolean isPhoneNumberValid = UserValidator.validatePhoneNumber(user.getPhoneNumber());
        boolean isNameValid = UserValidator.validateName(user.getName());
        if (isEmailValid && isPhoneNumberValid && isNameValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            return userDAO.updateInfo(user);
        }
        return false;
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