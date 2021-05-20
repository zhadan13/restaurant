package com.example.restaurant.db.dao;

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

    Optional<PasswordSalt> getByUserId(Long id);
}
