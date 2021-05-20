package com.example.restaurant.service;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Optional<Order> saveOrder(Order order);

    boolean deleteOrder(Long id);

    boolean updateOrder(Order order);

    boolean updateOrderDetails(Long id, Map<Product, Integer> products);

    boolean updateOrderStatus(Long id, OrderStatus status);

    Optional<Order> getOrder(Long id);

    List<Order> getAllOrders();

    List<Order> getUserOrders(Long id);

    List<Order> getUncompletedUserOrders(Long id);
}
