package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "authenticationFilter", urlPatterns = "/home")
public class AuthenticationFilter implements Filter {
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
            filterChain.doFilter(req, resp);
        } else {
            if (userRole != null && userRole.name().equals("MANAGER")) {
                resp.sendRedirect("/admin");
            } else {
                resp.sendRedirect("/login");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
