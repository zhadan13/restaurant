package com.example.restaurant.db.dao;

import com.example.restaurant.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAO<User> {
    @Override
    Optional<User> save(User user);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(User user);

    @Override
    Optional<User> get(Long id);

    @Override
    List<User> getAll();

    boolean updateInfo(User user);

    Optional<User> registration(User user);

    Optional<User> authorization(String email, String password);

    boolean checkIfUserExistsByUniqueParameters(User user);

    boolean checkIfUserExistsByEmailAndPassword(String email, String password);

    Optional<User> getByEmail(String email);
}
