package com.example.restaurant.controller.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class LocalizationFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    FilterChain filterChain;

    @Test
    void doFilter() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(httpServletRequest.getParameter("language")).thenReturn("ru");
        LocalizationFilter filter = new LocalizationFilter();
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        // then
        verify(session).setAttribute("locale", "ru_UA");
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void init() {
        // given
        LocalizationFilter filter = new LocalizationFilter();
        LocalizationFilter spyFilter = spy(filter);

        // when
        spyFilter.init(any());

        // then
        verify(spyFilter).init(any());
    }
}
