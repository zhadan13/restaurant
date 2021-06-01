package com.example.restaurant.controller.listener;

import com.example.restaurant.constants.Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("APPLICATION_NAME", Util.APPLICATION_NAME);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("APPLICATION_NAME");
    }
}
