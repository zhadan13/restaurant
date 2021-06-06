package com.example.restaurant.controller.listener;

import com.example.restaurant.constants.Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener that puts the name of the application in the context.
 *
 * @author Zhadan Artem
 * @see Util#APPLICATION_NAME
 */

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("APPLICATION_NAME", Util.APPLICATION_NAME);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("APPLICATION_NAME");
    }
}
