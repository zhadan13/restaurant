package com.example.restaurant.service.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.db.dao.OrderDAO;
import com.example.restaurant.db.dao.impl.OrderDAOImpl;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Order Service implementation.
 *
 * @author Zhadan Artem
 * @see Order
 * @see OrderService
 */

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    /**
     * Singleton instance.
     */
    private static OrderServiceImpl INSTANCE;

    /**
     * Constructs an <b>OrderServiceImpl</b>.
     */
    private OrderServiceImpl() {

    }

    /**
     * Returns already created instance of <b>OrderServiceImpl</b>, or creates new and then returns.
     *
     * @return {@link OrderServiceImpl} instance
     */
    public static OrderServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> saveOrder(Order order) {
        if (order.getAddress().trim().length() > 0 && order.getDate().isAfter(LocalDateTime.now())) {
            OrderDAO orderDAO = OrderDAOImpl.getInstance();
            Optional<Order> optionalOrder = orderDAO.save(order);
            if (optionalOrder.isPresent()) {
                LOGGER.info("Service: Order successfully saved. Order id: " + order.getId());
                return Optional.of(order);
            } else {
                LOGGER.error("Service: Can't save order");
            }
        } else {
            LOGGER.error("Service: Not valid order data");
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteOrder(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        boolean result = orderDAO.delete(id);
        if (result) {
            LOGGER.info("Service: Successfully deleted order. Order id: " + id);
        } else {
            LOGGER.error("Service: Can't delete order. Order id: " + id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrder(Order order) {
        if (order.getAddress().trim().length() > 0 && order.getDate().isAfter(LocalDateTime.now())) {
            OrderDAO orderDAO = OrderDAOImpl.getInstance();
            boolean result = orderDAO.update(order);
            if (result) {
                LOGGER.info("Service: Order successfully updated. Order id: " + order.getId());
            } else {
                LOGGER.error("Service: Can't update order. Order id:" + order.getId());
            }
            return result;
        }
        LOGGER.error("Service: Not valid order data");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrderDetails(Long id, Map<Product, Integer> products) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        boolean result = orderDAO.updateOrder(id, products);
        if (result) {
            LOGGER.info("Service: Order products successfully updated. Order id: " + id);
        } else {
            LOGGER.error("Service: Can't update order products. Order id:" + id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        boolean result = orderDAO.updateOrderStatus(id, status);
        if (result) {
            LOGGER.info("Service: Order status successfully updated. Order id: " + id);
        } else {
            LOGGER.error("Service: Can't update order status. Order id:" + id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> getOrder(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        Optional<Order> optionalOrder = orderDAO.get(id);
        if (optionalOrder.isPresent()) {
            LOGGER.info("Service: Order got successfully. Order id: " + id);
        } else {
            LOGGER.error("Service: Can't get order. Order id:" + id);
        }
        return optionalOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrders() {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getUserOrders(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAllUserOrders(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getUncompletedUserOrders(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAllUncompletedOrders(id);
    }
}
