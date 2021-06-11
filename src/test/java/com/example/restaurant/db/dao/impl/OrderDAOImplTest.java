package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.OrderDAO;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderProducts;
import com.example.restaurant.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderDAOImplTest {
    @InjectMocks
    Order order = Order.createOrder(1L, 1L, OrderStatus.ACCEPTED, "address", Payment.CASH, 450.0, LocalDateTime.now(), new HashMap<>());

    @InjectMocks
    OrderProducts orderProducts;

    @Mock
    ConnectionImpl connection;

    @Mock
    Pool pool;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    OrderDAO dao = OrderDAOImpl.getInstance();

    MockedStatic<Pool> mockedPool;

    @Mock
    OrderProductsDAOImpl orderProductsDAO;

    MockedStatic<OrderProductsDAOImpl> mockedSalt;

    @BeforeEach
    public void setUp() throws SQLException {
        mockedSalt = mockStatic(OrderProductsDAOImpl.class);
        mockedSalt.when(OrderProductsDAOImpl::getInstance).thenReturn(orderProductsDAO);
        mockedSalt.when(() -> orderProductsDAO.save(any(), any())).thenReturn(Optional.of(orderProducts));

        mockedPool = mockStatic(Pool.class);
        mockedPool.when(Pool::getInstance).thenReturn(pool);
        mockedPool.when(() -> pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);

        doNothing().when(connection).commit();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());

        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenAnswer(invocationOnMock -> null);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Test error!"));
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
    }

    @AfterEach
    void close() {
        mockedSalt.close();
        mockedPool.close();
    }

    @Test
    void save() {
        // when
        Optional<Order> optional = dao.save(order);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void delete() {
        // when
        boolean result = dao.delete(1L);

        // then
        assertTrue(result);
    }

    @Test
    void update() {
        // when
        boolean result = dao.update(order);

        // then
        assertTrue(result);
    }

    @Test
    void get() {
        // when
        Optional<Order> optional = dao.get(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAll() {
        // when
        List<Order> orders = dao.getAll();

        // then
        assertEquals(new ArrayList<>(), orders);
    }

    @Test
    void updateOrder() {
        // given
        Map<Product, Integer> map = new HashMap<>();
        map.put(Product.createProduct(), 10);

        // when
        boolean result = dao.updateOrder(1L, map);

        // then
        assertFalse(result);
    }

    @Test
    void updateOrderStatus() {
        // when
        boolean result = dao.updateOrderStatus(1L, OrderStatus.REJECTED);

        // then
        assertTrue(result);
    }

    @Test
    void getAllUserOrders() {
        // when
        List<Order> orders = dao.getAllUserOrders(1L);

        // then
        assertEquals(new ArrayList<>(), orders);
    }

    @Test
    void getAllUncompletedOrders() {
        // when
        List<Order> orders = dao.getAllUserOrders(1L);

        // then
        assertEquals(new ArrayList<>(), orders);
    }

    @Test
    void getAllProductsByOrderId() {
        // when
        Map<Product, Integer> orders = dao.getAllProductsByOrderId(1L);

        // then
        assertEquals(new HashMap<>(), orders);
    }
}
