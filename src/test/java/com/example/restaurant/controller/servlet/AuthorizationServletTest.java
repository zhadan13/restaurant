package com.example.restaurant.controller.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost() throws IOException, ServletException {
        // given
        AuthorizationServlet servlet = new AuthorizationServlet();
        AuthorizationServlet spyServlet = spy(servlet);

        // when
        spyServlet.doPost(httpServletRequest, httpServletResponse);

        // then
        verify(spyServlet).doGet(httpServletRequest, httpServletResponse);
    }

    @Test
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getParameter("user")).thenReturn("number");
        when(httpServletRequest.getParameter("token")).thenReturn("123456");
        AuthorizationServlet servlet = new AuthorizationServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(httpServletResponse).sendRedirect(any());
    }
}
