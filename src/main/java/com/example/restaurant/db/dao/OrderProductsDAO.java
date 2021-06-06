package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.model.OrderProducts;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a OrderProducts Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see OrderProducts
 */

public interface OrderProductsDAO extends DAO<OrderProducts> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<OrderProducts> save(OrderProducts orderProducts);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(OrderProducts orderProducts);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<OrderProducts> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<OrderProducts> getAll();

    /**
     * The same as {@link #save(OrderProducts)} method, but with parameter connection on which perform this method.
     *
     * @param orderProducts {@link OrderProducts} object, which has to be saved
     * @param connection    connection on which perform save method
     * @return {@link Optional<OrderProducts>#of(Object)} if authorization operation success,
     * or {@link Optional<OrderProducts>#empty()} otherwise
     */
    Optional<OrderProducts> save(OrderProducts orderProducts, ConnectionImpl connection);
}
