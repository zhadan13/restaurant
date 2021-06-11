package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.impl.AuthorizationTokenDAOImpl;
import com.example.restaurant.model.AuthorizationToken;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class AuthorizationTokenServiceImplTest {
    @InjectMocks
    AuthorizationToken token;

    @InjectMocks
    AuthorizationTokenServiceImpl service;

    @Mock(lenient = true)
    AuthorizationTokenDAOImpl dao;

    MockedStatic<AuthorizationTokenDAOImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(AuthorizationTokenDAOImpl.class);
        mocked.when(AuthorizationTokenDAOImpl::getInstance).thenReturn(dao);
        mocked.when(() -> dao.save(any())).thenReturn(Optional.of(token));
        mocked.when(() -> dao.update(any())).thenReturn(true);
        List<AuthorizationToken> tokens = new ArrayList<>();
        tokens.add(token);
        mocked.when(() -> dao.getAll()).thenReturn(tokens);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void saveToken() {
        // when
        Optional<AuthorizationToken> optional = service.saveToken(token);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void deleteToken() {
        // when
        boolean result = service.deleteToken(1L);

        // then
        assertFalse(result);
    }

    @Test
    void updateToken() {
        // when
        boolean result = service.updateToken(token);

        // then
        assertTrue(result);
    }

    @Test
    void getToken() {
        // when
        Optional<AuthorizationToken> optional = service.getToken(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAllTokens() {
        // when
        List<AuthorizationToken> tokens = service.getAllTokens();

        // then
        List<AuthorizationToken> expectedTokens = new ArrayList<>();
        expectedTokens.add(token);
        assertEquals(expectedTokens, tokens);
    }

    @Test
    void getUserToken() {
        // when
        Optional<AuthorizationToken> optional = service.getUserToken(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }
}
