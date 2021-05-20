package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "roleFilter", urlPatterns = "/admin")
public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Role userRole = (Role) req.getSession().getAttribute("userRole");
        if (userRole != null && userRole.name().equals("MANAGER")) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/home");
        }
    }
}
