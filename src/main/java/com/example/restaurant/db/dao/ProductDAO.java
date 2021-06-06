package com.example.restaurant.db.dao;

import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a Product Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see Product
 */

public interface ProductDAO extends DAO<Product> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<Product> save(Product product);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(Product product);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<Product> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Product> getAll();

    /**
     * Updating product popularity in database by product identifier.
     * If identifier null, or row with this id does not exists in table returns <tt>false</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>false</tt>.
     * Otherwise it performs updating and returns <tt>true</tt>.
     *
     * @param id         identifier of product, which popularity has to be updated
     * @param popularity status to be changed to
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     * @see Product
     */
    boolean updatePopularity(Long id, Integer popularity);
}
