package com.example.restaurant.db.dao;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDAO extends DAO<Order> {
    @Override
    Optional<Order> save(Order order);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(Order order);

    @Override
    Optional<Order> get(Long id);

    @Override
    List<Order> getAll();

    boolean updateOrder(Long id, Map<Product, Integer> products);

    boolean updateOrderStatus(Long id, OrderStatus status);

    List<Order> getAllUserOrders(Long id);

    List<Order> getAllUncompletedOrders(Long id);

    Map<Product, Integer> getAllProductsByOrderId(Long id);
}
