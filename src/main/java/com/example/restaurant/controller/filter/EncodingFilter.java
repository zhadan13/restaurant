package com.example.restaurant.controller.filter;

import com.example.restaurant.constants.Util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(Util.DEFAULT_ENCODING);
        servletResponse.setCharacterEncoding(Util.DEFAULT_ENCODING);
        servletResponse.setContentType("text/html; charset=" + Util.DEFAULT_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
