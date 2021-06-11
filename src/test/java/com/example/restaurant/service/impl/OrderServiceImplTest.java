package com.example.restaurant.service.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.db.dao.impl.OrderDAOImpl;
import com.example.restaurant.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    Order order;

    @InjectMocks
    OrderServiceImpl service;

    @Mock(lenient = true)
    OrderDAOImpl dao;

    MockedStatic<OrderDAOImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(OrderDAOImpl.class);
        mocked.when(OrderDAOImpl::getInstance).thenReturn(dao);
        mocked.when(() -> dao.save(any())).thenReturn(Optional.of(order));
        mocked.when(() -> dao.updateOrderStatus(anyLong(), any())).thenReturn(true);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        mocked.when(() -> dao.getAll()).thenReturn(orders);
        mocked.when(() -> dao.getAllUserOrders(anyLong())).thenReturn(orders);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void saveOrder() {
        // when
        order.setAddress("address");
        order.setDate(LocalDateTime.MAX);
        Optional<Order> optional = service.saveOrder(order);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void deleteOrder() {
        assertFalse(service.deleteOrder(1L));
    }

    @Test
    void updateOrder() {
        assertFalse(service.deleteOrder(1L));
    }

    @Test
    void updateOrderDetails() {
        assertFalse(service.updateOrderDetails(1L, new HashMap<>()));
    }

    @Test
    void updateOrderStatus() {
        assertTrue(service.updateOrderStatus(1L, OrderStatus.COMPLETED));
    }

    @Test
    void getOrder() {
        // when
        Optional<Order> optional = service.getOrder(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAllOrders() {
        // when
        List<Order> orders = service.getAllOrders();

        // then
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(order);
        assertEquals(expectedOrders, orders);
    }

    @Test
    void getUserOrders() {
        // when
        List<Order> orders = service.getUserOrders(1L);

        // then
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(order);
        assertEquals(expectedOrders, orders);
    }

    @Test
    void getUncompletedUserOrders() {
        // when
        List<Order> orders = service.getUncompletedUserOrders(1L);

        // then
        assertEquals(new ArrayList<>(), orders);
    }
}
