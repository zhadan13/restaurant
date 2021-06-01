package com.example.restaurant.db.dao;

import com.example.restaurant.db.connection_pool.Pool;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Pool POOL = Pool.getInstance();

    Optional<T> save(T t);

    boolean delete(Long id);

    boolean update(T t);

    Optional<T> get(Long id);

    List<T> getAll();
}
