package com.example.restaurant.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter setting localization for each page.
 *
 * @author Zhadan Artem
 */

@WebFilter(filterName = "localizationFilter", urlPatterns = "/*")
public class LocalizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LocalizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String locale = req.getParameter("language");
        if (locale != null) {
            if (locale.equals("en")) {
                session.setAttribute("locale", "en_US");
            }
            if (locale.equals("ru")) {
                session.setAttribute("locale", "ru_UA");
            }
            if (!locale.equals("en") && !locale.equals("ru")) {
                LOGGER.warn("Can't change locale");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
