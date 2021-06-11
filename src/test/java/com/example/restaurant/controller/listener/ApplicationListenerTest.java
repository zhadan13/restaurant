package com.example.restaurant.controller.listener;

import com.example.restaurant.constants.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationListenerTest {
    @Test
    void contextInitialized() {
        // given
        final ServletContextEvent event = mock(ServletContextEvent.class);
        final ServletContext context = mock(ServletContext.class);

        // when
        when(event.getServletContext()).thenReturn(context);
        final ApplicationListener applicationListener = new ApplicationListener();
        applicationListener.contextInitialized(event);

        // then
        verify(context).setAttribute(eq("APPLICATION_NAME"), eq(Util.APPLICATION_NAME));
    }

    @Test
    void contextDestroyed() {
        // given
        final ServletContextEvent event = mock(ServletContextEvent.class);
        final ServletContext context = mock(ServletContext.class);


        // when
        when(event.getServletContext()).thenReturn(context);
        final ApplicationListener applicationListener = new ApplicationListener();
        applicationListener.contextDestroyed(event);

        // then
        verify(context).removeAttribute(eq("APPLICATION_NAME"));
    }
}
