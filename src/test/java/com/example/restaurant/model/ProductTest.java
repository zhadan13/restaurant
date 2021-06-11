package com.example.restaurant.model;

import static com.example.restaurant.constants.MenuCategories.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void testEquals() {
        Product product = Product.createProduct("Name", "Details", PIZZA, 150.0, 600.0, 30);
        Product product1 = Product.createProduct("Name", "Details", PIZZA, 150.0, 600.0, 30);
        assertEquals(product, product1);
    }

    @Test
    void testNotEquals() {
        Product product = Product.createProduct("Name", "Details", PIZZA, 150.0, 600.0, 30);
        Product product1 = Product.createProduct("Name", "Details", PIZZA, 200.0, 600.0, 30);
        assertNotEquals(product, product1);
    }

    @Test
    void testHashCodeEquals() {
        Product product = Product.createProduct(1L, "Name", "Details", PIZZA, 150.0, 600.0, 30);
        Product product1 = Product.createProduct(1L, "Name", "Details", PIZZA, 150.0, 600.0, 30);
        assertEquals(product.hashCode(), product1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        Product product = Product.createProduct(1L, "Name", "Details", PIZZA, 150.0, 600.0, 30);
        Product product1 = Product.createProduct(1L, "Name", "Details", PIZZA, 200.0, 600.0, 30);
        assertNotEquals(product.hashCode(), product1.hashCode());
    }
}
