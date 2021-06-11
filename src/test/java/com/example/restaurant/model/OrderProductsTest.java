package com.example.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductsTest {
    @Test
    void testEquals() {
        OrderProducts orderProducts = OrderProducts.createOrderProducts(1L, 2L);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(1L, 2L);
        assertEquals(orderProducts, orderProducts1);
    }

    @Test
    void testNotEquals() {
        OrderProducts orderProducts = OrderProducts.createOrderProducts(1L, 2L);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(1L, 3L);
        assertNotEquals(orderProducts, orderProducts1);
    }

    @Test
    void testHashCodeEquals() {
        OrderProducts orderProducts = OrderProducts.createOrderProducts(1L, 2L);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(1L, 2L);
        assertEquals(orderProducts.hashCode(), orderProducts1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        OrderProducts orderProducts = OrderProducts.createOrderProducts(1L, 2L);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(1L, 3L);
        assertNotEquals(orderProducts.hashCode(), orderProducts1.hashCode());
    }
}
