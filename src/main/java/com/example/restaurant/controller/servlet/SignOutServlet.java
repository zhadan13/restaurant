package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet mapping signOut page.
 * This servlet invalidates the session keeping the localization.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "signOut", urlPatterns = "/signOut")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String locale = (String) session.getAttribute("locale");
        session.invalidate();
        session = req.getSession();
        session.setAttribute("locale", locale);
        resp.sendRedirect(Util.APPLICATION_NAME);
    }
}
