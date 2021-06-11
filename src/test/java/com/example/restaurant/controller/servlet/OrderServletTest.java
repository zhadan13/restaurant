package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.User;
import com.example.restaurant.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServletTest {
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

    @InjectMocks
    User user;

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
        when(httpServletRequest.getParameter("address")).thenReturn("address");
        when(httpServletRequest.getParameter("time")).thenReturn(LocalDateTime.now().toString());
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("totalPrice")).thenReturn(300.);
        when(session.getAttribute("deliveryPrice")).thenReturn(0.);
        when(session.getAttribute("bucket")).thenReturn(new HashMap<>());
        when(httpServletResponse.getWriter()).thenReturn(writer);
        when(httpServletRequest.getParameter("payment")).thenReturn("3");
        when(service.saveOrder(any())).thenReturn(Optional.empty());
        OrderServlet servlet = new OrderServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        // then
        verify(service).saveOrder(any());
        verify(httpServletResponse).getWriter();
    }

    @Test
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getRequestDispatcher("order.jsp")).thenReturn(dispatcher);
        OrderServlet servlet = new OrderServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
