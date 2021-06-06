package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.Pool;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a Generic Data Access Object that can be used for a single specified type object.
 * A single instance implementing this interface can be used for the type of object specified in the type parameters.
 *
 * @param <T> The type of the object for which this instance is to be used
 * @author Zhadan Artem
 */

public interface DAO<T> {
    /**
     * Cached connection pool used for DAO.
     *
     * @see Pool
     */
    Pool POOL = Pool.getInstance();

    /**
     * Saving entity to database.
     * If entity null, or row with unique parameters already exists in table returns <tt>Optional.empty()</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>Optional.empty()</tt>.
     * Otherwise it performs saving and returns <tt>Optional.of(T)</tt>.
     *
     * @param t object of specified type, which has to be saved
     * @return {@link Optional<T>#of(T)} if save operation success, or {@link Optional<T>#empty()} otherwise
     */
    Optional<T> save(T t);

    /**
     * Deleting row from database by identifier.
     * If identifier null, or row with this id does not exists in table returns <tt>false</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>false</tt>.
     * Otherwise it performs deleting and returns <tt>true</tt>.
     *
     * @param id identifier of row in table, which has to be deleted
     * @return <tt>true</tt> if delete operation success, or <tt>false</tt> otherwise
     */
    boolean delete(Long id);

    /**
     * Updating entity in database.
     * If entity null, or row with entity id does not exists in table returns <tt>false</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>false</tt>.
     * Otherwise it performs updating and returns <tt>true</tt>.
     *
     * @param t object of specified type, which has to be updated
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     */
    boolean update(T t);

    /**
     * Getting row from database by identifier.
     * If identifier null, or row with this id does not exists in table returns <tt>Optional.empty()</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>Optional.empty()</tt>.
     * Otherwise it performs operation and returns <tt>Optional.of(T)</tt>.
     *
     * @param id identifier of row in table, which has to be gotten
     * @return {@link Optional<T>#of(T)} if get operation success, or {@link Optional<T>#empty()} otherwise
     */
    Optional<T> get(Long id);

    /**
     * Getting list of all rows from database.
     * If table empty, returns empty list.
     * Otherwise returns list of rows.
     *
     * @return {@link List<T>}
     */
    List<T> getAll();
}
