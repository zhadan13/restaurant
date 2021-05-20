package com.example.restaurant.service;

import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> saveProduct(Product product);

    boolean deleteProduct(Long id);

    boolean updateProduct(Product product);

    boolean updateProductPopularity(Long id, Integer popularity);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProducts();
}
