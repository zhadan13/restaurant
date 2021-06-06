package com.example.restaurant.service;

import com.example.restaurant.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a Product Service.
 *
 * @author Zhadan Artem
 * @see Product
 */

public interface ProductService {
    /**
     * Performing saving product.
     *
     * @param product {@link Product} product, which has to be saved
     * @return {@link Optional<Product>#of(Object)} if save success, or {@link Optional<Product>#empty()} otherwise
     * @see Product
     */
    Optional<Product> saveProduct(Product product);

    /**
     * Performing product delete.
     *
     * @param id product's identifier
     * @return <tt>true</tt> if delete success, or <tt>false</tt> otherwise
     * @see Product
     */
    boolean deleteProduct(Long id);

    /**
     * Performing product update.
     *
     * @param product {@link Product} product, which has to be updated
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see Product
     */
    boolean updateProduct(Product product);

    /**
     * Performing product popularity update.
     *
     * @param id         product's identifier
     * @param popularity product popularity
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see Product
     */
    boolean updateProductPopularity(Long id, Integer popularity);

    /**
     * Getting product.
     *
     * @param id product's identifier
     * @return {@link Optional<Product>#of(Object)} if get success, or {@link Optional<Product>#empty()} otherwise
     * @see Product
     */
    Optional<Product> getProduct(Long id);

    /**
     * Getting all products.
     *
     * @return {@link List} of products
     * @see Product
     */
    List<Product> getAllProducts();
}
