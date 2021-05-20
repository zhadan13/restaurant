package com.example.restaurant.service.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.db.dao.OrderDAO;
import com.example.restaurant.db.dao.impl.OrderDAOImpl;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl INSTANCE;

    private OrderServiceImpl() {

    }

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

    @Override
    public Optional<Order> saveOrder(Order order) {
        if (order.getAddress().trim().length() > 0 && order.getDate().after(Timestamp.valueOf(LocalDateTime.now()))) {
            OrderDAO orderDAO = OrderDAOImpl.getInstance();
            return orderDAO.save(order);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteOrder(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.delete(id);
    }

    @Override
    public boolean updateOrder(Order order) {
        if (order.getAddress().trim().length() > 0 && order.getDate().after(Timestamp.valueOf(LocalDateTime.now()))) {
            OrderDAO orderDAO = OrderDAOImpl.getInstance();
            return orderDAO.update(order);
        }
        return false;
    }

    @Override
    public boolean updateOrderDetails(Long id, Map<Product, Integer> products) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.updateOrder(id, products);
    }

    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.updateOrderStatus(id, status);
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.get(id);
    }

    @Override
    public List<Order> getAllOrders() {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAll();
    }

    @Override
    public List<Order> getUserOrders(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAllUserOrders(id);
    }

    @Override
    public List<Order> getUncompletedUserOrders(Long id) {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        return orderDAO.getAllUncompletedOrders(id);
    }
}
