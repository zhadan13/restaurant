package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    User user;

    @Test
    void doFilter() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        user.setRole(Role.USER);
        when(session.getAttribute("user")).thenReturn(user);
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        // then
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void init() {
        // given
        AuthenticationFilter filter = new AuthenticationFilter();
        AuthenticationFilter spyFilter = spy(filter);

        // when
        spyFilter.init(any());

        // then
        verify(spyFilter).init(any());
    }
}
