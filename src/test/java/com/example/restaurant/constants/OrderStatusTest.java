package com.example.restaurant.constants;

import com.example.restaurant.model.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.restaurant.constants.OrderStatus.*;
import static com.example.restaurant.constants.Payment.CASH;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void parseStatus() {
        assertEquals(COMPLETED, OrderStatus.parseStatus("completed"));
    }

    @Test
    void parseStatusWrong() {
        assertNull(OrderStatus.parseStatus("blocked"));
    }

    @Test
    void filter() {
        // given
        List<Order> orders = new ArrayList<>();
        orders.add(Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 19, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, PREPARING, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 21, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 22, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 23, 0), new HashMap<>()));

        // when
        orders = OrderStatus.filter(orders, "REJECTED", "DATE NEW TO OLD");

        // then
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 23, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 22, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, REJECTED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>()));
        assertEquals(orders, expectedOrders);
    }

    @Test
    void filterDefault() {
        // given
        List<Order> orders = new ArrayList<>();
        orders.add(Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 19, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, PREPARING, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 21, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 22, 0), new HashMap<>()));
        orders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 23, 0), new HashMap<>()));

        // when
        orders = OrderStatus.filter(orders, "default", "DEFAULT");

        // then
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(Order.createOrder(1L, CONFIRMED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 19, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 20, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, PREPARING, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 21, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 22, 0), new HashMap<>()));
        expectedOrders.add(Order.createOrder(1L, COMPLETED, "Address", CASH, 250.0, LocalDateTime.of(2021, 6, 5, 23, 0), new HashMap<>()));
        assertEquals(orders, expectedOrders);
    }
}
