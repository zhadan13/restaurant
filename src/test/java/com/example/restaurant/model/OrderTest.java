package com.example.restaurant.model;

import static com.example.restaurant.constants.OrderStatus.*;
import static com.example.restaurant.constants.Payment.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testEquals() {
        Order order = Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        Order order1 = Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        assertEquals(order, order1);
    }

    @Test
    void testNotEquals() {
        Order order = Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        Order order1 = Order.createOrder(1L, CONFIRMED, "Address", CARD_ONLINE, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        assertNotEquals(order, order1);
    }

    @Test
    void testHashCodeEquals() {
        Order order = Order.createOrder(1L, 1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        Order order1 = Order.createOrder(1L, 1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        assertEquals(order.hashCode(), order1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        Order order = Order.createOrder(1L, 1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        Order order1 = Order.createOrder(1L, 1L, CONFIRMED, "Address", CARD_ONLINE, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>());
        assertNotEquals(order.hashCode(), order1.hashCode());
    }
}
