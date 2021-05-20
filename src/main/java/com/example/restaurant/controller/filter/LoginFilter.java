package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = {"/login", "/register"})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String userEmail = (String) req.getSession().getAttribute("userEmail");
        Role userRole = (Role) req.getSession().getAttribute("userRole");
        if (userEmail != null && userRole != null && userRole.name().equals("USER")) {
            resp.sendRedirect("/home");
        } else {
            if (userRole != null && userRole.name().equals("MANAGER")) {
                resp.sendRedirect("/admin");
            } else {
                filterChain.doFilter(req, resp);
            }
        }
    }
}
