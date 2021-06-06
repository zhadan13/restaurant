package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.model.PasswordSalt;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a PasswordSalt Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see PasswordSalt
 */

public interface PasswordSaltDAO extends DAO<PasswordSalt> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<PasswordSalt> save(PasswordSalt passwordSalt);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(PasswordSalt passwordSalt);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<PasswordSalt> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<PasswordSalt> getAll();

    /**
     * The same as {@link #save(PasswordSalt)} method, but with parameter connection on which perform this method.
     *
     * @param passwordSalt {@link PasswordSalt} object, which has to be saved
     * @param connection   connection on which perform save method
     * @return {@link Optional<PasswordSalt>#of(Object)} if authorization operation success,
     * or {@link Optional<PasswordSalt>#empty()} otherwise
     */
    Optional<PasswordSalt> save(PasswordSalt passwordSalt, ConnectionImpl connection);

    /**
     * The same as {@link #update(PasswordSalt)} method, but with parameter connection on which perform this method.
     *
     * @param passwordSalt {@link PasswordSalt} object, which has to be updated
     * @param connection   connection on which perform update method
     * @return <tt>true</tt> if authorization operation success, or <tt>false</tt> otherwise
     */
    boolean update(PasswordSalt passwordSalt, ConnectionImpl connection);

    /**
     * Getting password salt from database by user identifier.
     *
     * @param id user identifier
     * @return {@link Optional<PasswordSalt>#of(Object)} if get operation success,
     * or {@link Optional<PasswordSalt>#empty()} otherwise
     */
    Optional<PasswordSalt> getByUserId(Long id);
}
