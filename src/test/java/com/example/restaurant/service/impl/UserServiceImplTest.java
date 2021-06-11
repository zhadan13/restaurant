package com.example.restaurant.service.impl;

import com.example.restaurant.constants.Role;
import com.example.restaurant.db.dao.impl.UserDAOImpl;
import com.example.restaurant.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    User user = User.createUser(1L, "user@mail.com", "123456Aa".toCharArray(), "0500000000", "user", Role.USER, true);

    @InjectMocks
    UserServiceImpl service;

    @Mock(lenient = true)
    UserDAOImpl dao;

    MockedStatic<UserDAOImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(UserDAOImpl.class);
        mocked.when(UserDAOImpl::getInstance).thenReturn(dao);
        mocked.when(() -> dao.authorization(anyString(), anyString())).thenReturn(Optional.of(user));
        mocked.when(() -> dao.updateAuthorizationStatus(anyLong(), anyBoolean())).thenReturn(true);
        mocked.when(() -> dao.get(anyLong())).thenReturn(Optional.of(user));
        mocked.when(() -> dao.delete(anyLong())).thenReturn(true);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void registrationShouldBeFailed() {
        // when
        Optional<User> optional = service.registration(user);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void authorizationShouldBeSuccessful() {
        // when
        Optional<User> optional = service.authorization("user@mail.com", "123456Aa");

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void validation() {
        // when
        boolean result = service.validation(user);

        // then
        assertTrue(result);
    }

    @Test
    void updateUser() {
        // when
        boolean result = service.updateUser(user);

        // then
        assertFalse(result);
    }

    @Test
    void updateUserInformation() {
        // when
        boolean result = service.updateUserInformation(user);

        // then
        assertFalse(result);
    }

    @Test
    void updateAuthorizationStatus() {
        // when
        boolean result = service.updateAuthorizationStatus(user.getId());

        // then
        assertTrue(result);
    }

    @Test
    void getUser() {
        // when
        Optional<User> optional = service.getUser(1L);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void getUserByEmail() {
        // when
        Optional<User> optional = service.getUser("mail");

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAllUsers() {
        // when
        List<User> users = service.getAllUsers();

        // then
        assertEquals(new ArrayList<>(), users);
    }

    @Test
    void deleteUser() {
        // when
        boolean result = service.deleteUser(1L);

        // then
        assertTrue(result);
    }
}
