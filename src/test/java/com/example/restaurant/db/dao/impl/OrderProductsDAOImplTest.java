package com.example.restaurant.db.dao.impl;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.OrderProductsDAO;
import com.example.restaurant.model.OrderProducts;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderProductsDAOImplTest {
    @InjectMocks
    OrderProducts orderProducts = OrderProducts.createOrderProducts(1L, 1L);

    @Mock
    ConnectionImpl connection;

    @Mock
    Pool pool;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    OrderProductsDAO dao = OrderProductsDAOImpl.getInstance();

    MockedStatic<Pool> mockedPool;

    @BeforeEach
    public void setUp() throws SQLException {
        mockedPool = mockStatic(Pool.class);
        mockedPool.when(Pool::getInstance).thenReturn(pool);
        mockedPool.when(() -> pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        doNothing().when(connection).commit();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());

        when(preparedStatement.executeUpdate()).thenAnswer(invocationOnMock -> null);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Test error!"));
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
    }

    @AfterEach
    void close() {
        mockedPool.close();
    }

    @Test
    void save() {
        // when
        Optional<OrderProducts> optional = dao.save(orderProducts);

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
    void get() {
        // when
        Optional<OrderProducts> optional = dao.get(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAll() {
        // when
        List<OrderProducts> orderProducts = dao.getAll();

        // then
        assertEquals(new ArrayList<>(), orderProducts);
    }

    @Test
    void saveOnConnection() {
        // when
        Optional<OrderProducts> optional = dao.save(orderProducts, connection);

        // then
        assertNotEquals(Optional.empty(), optional);
    }
}
