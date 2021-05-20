package com.example.restaurant.db.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> save(T t);

    boolean delete(Long id);

    boolean update(T t);

    Optional<T> get(Long id);

    List<T> getAll();
}
