package com.example.restaurant.db.dao;

import com.example.restaurant.model.AuthorizationToken;

import java.util.List;
import java.util.Optional;

public interface AuthorizationTokenDAO extends DAO<AuthorizationToken> {
    @Override
    Optional<AuthorizationToken> save(AuthorizationToken authorizationToken);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(AuthorizationToken authorizationToken);

    @Override
    Optional<AuthorizationToken> get(Long id);

    @Override
    List<AuthorizationToken> getAll();

    Optional<AuthorizationToken> getByUserId(Long id);
}
