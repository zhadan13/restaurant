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
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

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
    void doPost() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(service.getUserOrders(anyLong())).thenReturn(new ArrayList<>());
        when(httpServletRequest.getParameter("removeOrder")).thenReturn("number");
        AccountServlet servlet = new AccountServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        // then
        verify(httpServletResponse).sendRedirect(any());
    }

    @Test
    void doGet() throws ServletException, IOException {
        // given
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(service.getUserOrders(anyLong())).thenReturn(new ArrayList<>());
        when(httpServletRequest.getRequestDispatcher("account.jsp")).thenReturn(dispatcher);
        AccountServlet servlet = new AccountServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
