package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.User;
import com.example.restaurant.util.SendMail;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuccessOrderServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher dispatcher;

    @InjectMocks
    User user;

    @InjectMocks
    Order order;

    MockedStatic<SendMail> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(SendMail.class);
        mocked.when(() -> SendMail.sendOrderMail(user, order.getId())).thenAnswer(invocationOnMock -> null);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void doGet() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("order")).thenReturn(order);
        when(httpServletRequest.getRequestDispatcher("successOrder.jsp")).thenReturn(dispatcher);
        order.setId(1L);
        SuccessOrderServlet servlet = new SuccessOrderServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        mocked.verify(() -> SendMail.sendOrderMail(user, order.getId()), times(1));
        verify(httpServletRequest).setAttribute("order", order);
        verify(session).removeAttribute("totalPrice");
        verify(session).removeAttribute("productsInBucket");
        verify(session).removeAttribute("bucket");
        verify(session).removeAttribute("order");
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
