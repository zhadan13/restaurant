package com.example.restaurant.db.dao;

import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO extends DAO<Product> {
    @Override
    Optional<Product> save(Product product);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(Product product);

    @Override
    Optional<Product> get(Long id);

    @Override
    List<Product> getAll();

    boolean updatePopularity(Long id, Integer popularity);
}
