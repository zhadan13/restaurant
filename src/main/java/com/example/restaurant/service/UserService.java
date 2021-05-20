package com.example.restaurant.service;

import com.example.restaurant.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> registration(User user);

    Optional<User> authorization(String email, String password);

    boolean updateUser(User user);

    boolean updateUserInformation(User user);

    Optional<User> getUser(Long id);

    Optional<User> getUser(String email);

    List<User> getAllUsers();

    boolean deleteUser(Long id);
}
