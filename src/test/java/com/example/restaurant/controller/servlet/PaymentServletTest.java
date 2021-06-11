package com.example.restaurant.controller.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    PrintWriter writer;

    @Mock
    RequestDispatcher dispatcher;

    @Test
    void doPost() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(httpServletResponse.getWriter()).thenReturn(writer);
        PaymentServlet servlet = new PaymentServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        // then
        verify(httpServletResponse).getWriter();
    }

    @Test
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getRequestDispatcher("payment.jsp")).thenReturn(dispatcher);
        PaymentServlet servlet = new PaymentServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
