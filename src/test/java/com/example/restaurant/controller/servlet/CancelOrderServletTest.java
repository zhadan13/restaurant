package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Util;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @InjectMocks
    Order order;

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
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        order.setId(1L);
        when(session.getAttribute("order")).thenReturn(order);
        when(service.updateOrderStatus(anyLong(), any())).thenReturn(true);
        CancelOrderServlet servlet = new CancelOrderServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(session).removeAttribute("order");
        verify(httpServletResponse).sendRedirect(Util.APPLICATION_NAME);
    }
}
