package com.example.restaurant.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "signOut", urlPatterns = "/signOut")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            session.removeAttribute("userId");
            session.removeAttribute("userEmail");
            session.removeAttribute("userPhone");
            session.removeAttribute("userName");
            session.removeAttribute("userRole");
            session.removeAttribute("totalPrice");
            session.removeAttribute("productsInBucket");
            session.removeAttribute("bucket");
            session.removeAttribute("allUserOrders");
            session.removeAttribute("order");
            session.removeAttribute("sorting");
            session.removeAttribute("category");
            session.removeAttribute("products");
        }

        resp.sendRedirect("/");
    }
}
