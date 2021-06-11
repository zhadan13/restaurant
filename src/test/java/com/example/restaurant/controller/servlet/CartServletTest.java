package com.example.restaurant.controller.servlet;

import com.example.restaurant.service.impl.ProductServiceImpl;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher dispatcher;

    @Mock(lenient = true)
    ProductServiceImpl service;

    MockedStatic<ProductServiceImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(ProductServiceImpl.class);
        mocked.when(ProductServiceImpl::getInstance).thenReturn(service);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void doGetShouldRemoveProduct() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("productsInBucket")).thenReturn(null);
        when(httpServletRequest.getParameter("removeProduct")).thenReturn("1");
        CartServlet servlet = new CartServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(session).setAttribute(eq("productsInBucket"), any());
        verify(httpServletResponse).sendRedirect("cart");
    }

    @Test
    void doGetShouldCreateBucket() throws IOException, ServletException {
        // when
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("productsInBucket")).thenReturn(null);
        when(httpServletRequest.getParameter("removeProduct")).thenReturn(null);
        when(httpServletRequest.getRequestDispatcher(any())).thenReturn(dispatcher);
        CartServlet servlet = new CartServlet();
        servlet.doGet(httpServletRequest, httpServletResponse);

        // then
        verify(session).setAttribute(eq("bucket"), any());
        verify(session).setAttribute(eq("totalPrice"), any());
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
