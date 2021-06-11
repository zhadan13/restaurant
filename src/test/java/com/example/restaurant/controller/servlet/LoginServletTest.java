package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import com.example.restaurant.service.impl.UserServiceImpl;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {
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
    UserServiceImpl service;

    MockedStatic<UserServiceImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(UserServiceImpl.class);
        mocked.when(UserServiceImpl::getInstance).thenReturn(service);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void doPostShouldAuthorizeUser() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        user.setAuthorized(true);
        user.setRole(Role.MANAGER);
        when(service.authorization(any(), any())).thenReturn(Optional.of(user));
        LoginServlet servlet = new LoginServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        //then
        verify(service).authorization(any(), any());
        verify(httpServletResponse).sendRedirect("admin");
    }

    @Test
    void doPostShouldShowAlertWhenUserNotRegistered() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(httpServletResponse.getWriter()).thenReturn(writer);
        when(service.authorization(any(), any())).thenReturn(Optional.empty());
        LoginServlet servlet = new LoginServlet();
        servlet.doPost(httpServletRequest, httpServletResponse);

        //then
        verify(service).authorization(any(), any());
        verify(httpServletResponse).getWriter();
    }

    @Test
    void doGet() throws ServletException, IOException {
        // when
        when(httpServletRequest.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
