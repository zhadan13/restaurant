package com.example.restaurant.controller.filter;

import com.example.restaurant.model.Product;
import com.example.restaurant.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Filter restricting access to order page if user not logged or order not created.
 *
 * @author Zhadan Artem
 */

@WebFilter(filterName = "orderFilter", urlPatterns = "/order")
public class OrderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
        } else {
            Map<Product, Integer> bucket = (Map<Product, Integer>) req.getSession().getAttribute("bucket");
            if (bucket != null && bucket.size() > 0) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("home");
            }
        }
    }
}
