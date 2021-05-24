package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;

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

        User user = (User) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == Role.USER) {
            resp.sendRedirect("/home");
        } else {
            if (user != null && user.getRole() == Role.MANAGER) {
                resp.sendRedirect("/admin");
            } else {
                filterChain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
