package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.restaurant.constants.Util.DEFAULT_ENCODING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncodingFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    FilterChain filterChain;

    @Test
    void init() {
        // given
        EncodingFilter filter = new EncodingFilter();
        EncodingFilter spyFilter = spy(filter);

        // when
        spyFilter.init(any());

        // then
        verify(spyFilter).init(any());
    }

    @Test
    void doFilter() throws ServletException, IOException {
        // when
        EncodingFilter filter = new EncodingFilter();
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        // then
        verify(httpServletRequest).setCharacterEncoding(Util.DEFAULT_ENCODING);
        verify(httpServletResponse).setCharacterEncoding(Util.DEFAULT_ENCODING);
        verify(httpServletResponse).setContentType("text/html; charset=" + DEFAULT_ENCODING);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }
}
