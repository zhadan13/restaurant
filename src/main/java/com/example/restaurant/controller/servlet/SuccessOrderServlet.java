package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "successOrder", urlPatterns = "/successOrder")
public class SuccessOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Order order = (Order) session.getAttribute("order");
        if (order != null) {
            req.setAttribute("order", order);
        }
        session.removeAttribute("totalPrice");
        session.removeAttribute("productsInBucket");
        session.removeAttribute("bucket");
        session.removeAttribute("order");

        req.getRequestDispatcher("/successOrder.jsp").forward(req, resp);
    }
}
