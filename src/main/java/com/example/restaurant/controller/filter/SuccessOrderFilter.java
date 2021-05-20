package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.model.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "successOrderFilter", urlPatterns = "/successOrder")
public class SuccessOrderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String userEmail = (String) req.getSession().getAttribute("userEmail");
        if (userEmail == null) {
            resp.sendRedirect("/login");
        } else {
            Order order = (Order) req.getSession().getAttribute("order");
            if (order != null) {
                if (order.getPayment() != Payment.CARD_ONLINE) {
                    filterChain.doFilter(req, resp);
                } else {
                    if (order.getStatus() == OrderStatus.CONFIRMED) {
                        filterChain.doFilter(req, resp);
                    } else {
                        resp.sendRedirect("/home");
                    }
                }
            } else {
                resp.sendRedirect("/home");
            }
        }
    }
}
