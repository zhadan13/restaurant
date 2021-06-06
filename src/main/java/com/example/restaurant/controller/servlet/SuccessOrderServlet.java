package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.User;
import com.example.restaurant.util.SendMail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet mapping successOrder page.
 * This servlet sends email to user about order and clears session.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "successOrder", urlPatterns = "/successOrder")
public class SuccessOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        Order order = (Order) session.getAttribute("order");

        // Sending email to user with order details
        SendMail.sendOrderMail(user, order.getId());

        req.setAttribute("order", order);
        session.removeAttribute("totalPrice");
        session.removeAttribute("productsInBucket");
        session.removeAttribute("bucket");
        session.removeAttribute("order");

        req.getRequestDispatcher("successOrder.jsp").forward(req, resp);
    }
}
