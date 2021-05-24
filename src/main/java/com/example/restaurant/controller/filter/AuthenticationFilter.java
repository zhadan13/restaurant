package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;

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

        User user = (User) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == Role.USER) {
            filterChain.doFilter(req, resp);
        } else {
            if (user != null && user.getRole() == Role.MANAGER) {
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
