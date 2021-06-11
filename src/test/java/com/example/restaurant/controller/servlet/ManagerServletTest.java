package com.example.restaurant.controller.servlet;

import com.example.restaurant.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManagerServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher dispatcher;

    @Mock(lenient = true)
    OrderServiceImpl service;

    MockedStatic<OrderServiceImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(OrderServiceImpl.class);
        mocked.when(OrderServiceImpl::getInstance).thenReturn(service);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void doPost() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(service.getAllOrders()).thenReturn(new ArrayList<>());
        when(httpServletRequest.getParameter("removeOrder")).thenReturn("number");
        ManagerServlet servlet = new ManagerServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        //then
        verify(httpServletResponse).sendRedirect("admin");
    }

    @Test
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(httpServletRequest.getRequestDispatcher(any())).thenReturn(dispatcher);
        ManagerServlet servlet = new ManagerServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
