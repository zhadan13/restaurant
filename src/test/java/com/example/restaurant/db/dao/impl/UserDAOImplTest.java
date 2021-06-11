package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.Role;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.UserDAO;
import com.example.restaurant.model.PasswordSalt;
import com.example.restaurant.model.User;
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
class UserDAOImplTest {
    @InjectMocks
    User user = User.createUser(1L, "user@mail.com", "123456Aa".toCharArray(), "0500000000", "user", Role.USER, true);

    @InjectMocks
    PasswordSalt salt;

    @Mock
    ConnectionImpl connection;

    @Mock
    Pool pool;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    UserDAO dao = UserDAOImpl.getInstance();

    MockedStatic<Pool> mockedPool;

    @Mock
    PasswordSaltDAOImpl passwordSaltDAO;

    MockedStatic<PasswordSaltDAOImpl> mockedSalt;

    @BeforeEach
    public void setUp() throws SQLException {
        mockedSalt = mockStatic(PasswordSaltDAOImpl.class);
        mockedSalt.when(PasswordSaltDAOImpl::getInstance).thenReturn(passwordSaltDAO);
        mockedSalt.when(() -> passwordSaltDAO.save(any(), any())).thenReturn(Optional.of(salt));
        mockedSalt.when(() -> passwordSaltDAO.update(any(), any())).thenReturn(true);

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
        Optional<User> optional = dao.save(user);

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
        boolean result = dao.update(user);

        // then
        assertTrue(result);
    }

    @Test
    void get() {
        // when
        Optional<User> optional = dao.get(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAll() {
        // when
        List<User> list = dao.getAll();

        // then
        assertEquals(new ArrayList<>(), list);
    }

    @Test
    void updateInfo() {
        // when
        boolean result = dao.updateInfo(user);

        // then
        assertTrue(result);
    }

    @Test
    void updateAuthorizationStatus() {
        // when
        boolean result = dao.updateAuthorizationStatus(1L, true);

        // then
        assertTrue(result);
    }

    @Test
    void registration() {
        // when
        Optional<User> optional = dao.registration(user);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void authorization() {
        // when
        Optional<User> optional = dao.authorization(user.getEmail(), String.valueOf(user.getPassword()));

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void checkIfUserExistsByUniqueParameters() {
        // when
        boolean result = dao.checkIfUserExistsByUniqueParameters(user);

        // then
        assertFalse(result);
    }

    @Test
    void checkIfUserExistsByEmailAndPassword() {
        // when
        boolean result = dao.checkIfUserExistsByEmailAndPassword(user.getEmail(), String.valueOf(user.getPassword()));

        // then
        assertFalse(result);
    }

    @Test
    void getByEmail() {
        // when
        Optional<User> optional = dao.getByEmail(user.getEmail());

        // then
        assertEquals(Optional.empty(), optional);
    }
}
