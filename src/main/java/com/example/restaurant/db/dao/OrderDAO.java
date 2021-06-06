package com.example.restaurant.db.dao;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for a Order Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see Order
 */

public interface OrderDAO extends DAO<Order> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<Order> save(Order order);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(Order order);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<Order> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Order> getAll();

    /**
     * Updating order products in database.
     *
     * @param id       order id
     * @param products <tt>Map</tt> of products
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     * @see Order
     * @see Product
     */
    boolean updateOrder(Long id, Map<Product, Integer> products);

    /**
     * Updating order status in database.
     *
     * @param id     order id
     * @param status {@link OrderStatus} status to be changed to
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     * @see Order
     * @see OrderStatus
     */
    boolean updateOrderStatus(Long id, OrderStatus status);

    /**
     * Getting list of all user's orders by user's identifier from database.
     *
     * @return {@link List<Order>}
     */
    List<Order> getAllUserOrders(Long id);

    /**
     * Getting list of all user's uncompleted orders by user's identifier from database.
     *
     * @return {@link List<Order>}
     */
    List<Order> getAllUncompletedOrders(Long id);

    /**
     * Getting all order's products by order's identifier from database mapped into <tt>Map</tt>.
     *
     * @return {@link List<Order>}
     */
    Map<Product, Integer> getAllProductsByOrderId(Long id);
}
