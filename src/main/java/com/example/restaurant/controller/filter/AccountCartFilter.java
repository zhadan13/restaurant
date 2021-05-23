package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "accountCartFilter", urlPatterns = {"/account", "/cart"})
public class AccountCartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String userEmail = (String) req.getSession().getAttribute("userEmail");
        Role userRole = (Role) req.getSession().getAttribute("userRole");
        if (userEmail != null && Role.USER.equals(userRole)) {
            filterChain.doFilter(req, resp);
        } else {
            if (Role.MANAGER.equals(userRole)) {
                resp.sendRedirect("/admin");
            } else {
                resp.sendRedirect("/login");
            }
        }
    }
}
