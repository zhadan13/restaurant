package com.example.restaurant.db.dao;

import com.example.restaurant.model.AuthorizationToken;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a AuthorizationToken Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see AuthorizationToken
 */

public interface AuthorizationTokenDAO extends DAO<AuthorizationToken> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<AuthorizationToken> save(AuthorizationToken authorizationToken);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(AuthorizationToken authorizationToken);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<AuthorizationToken> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<AuthorizationToken> getAll();

    /**
     * Getting authorization token from database by user identifier.
     *
     * @param id user identifier
     * @return {@link Optional<AuthorizationToken>#of(Object)} if get operation success,
     * or {@link Optional<AuthorizationToken>#empty()} otherwise
     */
    Optional<AuthorizationToken> getByUserId(Long id);
}
