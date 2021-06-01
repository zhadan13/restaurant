package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.model.PasswordSalt;

import java.util.List;
import java.util.Optional;

public interface PasswordSaltDAO extends DAO<PasswordSalt> {
    @Override
    Optional<PasswordSalt> save(PasswordSalt passwordSalt);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(PasswordSalt passwordSalt);

    @Override
    Optional<PasswordSalt> get(Long id);

    @Override
    List<PasswordSalt> getAll();

    Optional<PasswordSalt> save(PasswordSalt passwordSalt, ConnectionImpl connection);

    boolean update(PasswordSalt passwordSalt, ConnectionImpl connection);

    Optional<PasswordSalt> getByUserId(Long id);
}
