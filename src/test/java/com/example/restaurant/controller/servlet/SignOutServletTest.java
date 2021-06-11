package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignOutServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Test
    void doGet() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(null);
        SignOutServlet servlet = new SignOutServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(session).invalidate();
        verify(session).setAttribute(eq("locale"), any());
        verify(httpServletResponse).sendRedirect(eq(Util.APPLICATION_NAME));
    }
}
