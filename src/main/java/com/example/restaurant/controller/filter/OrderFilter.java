package com.example.restaurant.controller.filter;

import com.example.restaurant.model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "orderFilter", urlPatterns = "/order")
public class OrderFilter implements Filter {
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
            Map<Product, Integer> bucketMap = (Map<Product, Integer>) req.getSession().getAttribute("bucket");
            if (bucketMap != null && bucketMap.size() > 0) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("/home");
            }
        }
    }
}
