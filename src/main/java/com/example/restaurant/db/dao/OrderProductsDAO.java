package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.model.OrderProducts;

import java.util.List;
import java.util.Optional;

public interface OrderProductsDAO extends DAO<OrderProducts> {
    @Override
    Optional<OrderProducts> save(OrderProducts orderProducts);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(OrderProducts orderProducts);

    @Override
    Optional<OrderProducts> get(Long id);

    @Override
    List<OrderProducts> getAll();

    Optional<OrderProducts> save(OrderProducts orderProducts, ConnectionImpl connection);

    boolean deleteById(OrderProducts orderProducts);
}
