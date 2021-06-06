package com.example.restaurant.controller.filter;

import com.example.restaurant.model.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter restricting access to payment page if order not created.
 *
 * @author Zhadan Artem
 */

@WebFilter(filterName = "paymentFilter", urlPatterns = {"/payment", "/cancelOrder"})
public class PaymentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Order order = (Order) req.getSession().getAttribute("order");
        if (order != null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("home");
        }
    }
}
