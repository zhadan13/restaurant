package com.example.restaurant.service;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for a Order Service.
 *
 * @author Zhadan Artem
 * @see Order
 */

public interface OrderService {
    /**
     * Performing validating and saving order.
     *
     * @param order {@link Order} order, which has to be saved
     * @return {@link Optional<Order>#of(Object)} if save success, or {@link Optional<Order>#empty()} otherwise
     * @see Order
     */
    Optional<Order> saveOrder(Order order);

    /**
     * Performing order delete.
     *
     * @param id order's identifier
     * @return <tt>true</tt> if delete success, or <tt>false</tt> otherwise
     * @see Order
     */
    boolean deleteOrder(Long id);

    /**
     * Performing validating and updating order.
     *
     * @param order {@link Order} order, which has to be updated
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see Order
     */
    boolean updateOrder(Order order);

    /**
     * Performing order's products update.
     *
     * @param id       order's identifier
     * @param products <tt>Map</tt> of products
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see Order
     */
    boolean updateOrderDetails(Long id, Map<Product, Integer> products);

    /**
     * Performing order's products update.
     *
     * @param id     order's identifier
     * @param status {@link OrderStatus} order's status
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see Order
     * @see OrderStatus
     */
    boolean updateOrderStatus(Long id, OrderStatus status);

    /**
     * Getting order.
     *
     * @param id order's identifier
     * @return {@link Optional<Order>#of(Object)} if get success, or {@link Optional<Order>#empty()} otherwise
     * @see Order
     */
    Optional<Order> getOrder(Long id);

    /**
     * Getting all orders.
     *
     * @return {@link List} of orders
     * @see Order
     */
    List<Order> getAllOrders();

    /**
     * Getting all user's orders.
     *
     * @return {@link List} of user's orders
     * @see Order
     */
    List<Order> getUserOrders(Long id);

    /**
     * Getting all user's uncompleted orders.
     *
     * @return {@link List} of user's uncompleted orders
     * @see Order
     */
    List<Order> getUncompletedUserOrders(Long id);
}
