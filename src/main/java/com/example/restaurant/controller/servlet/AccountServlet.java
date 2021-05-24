package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.Order;
import com.example.restaurant.model.User;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "account", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        OrderService orderService = OrderServiceImpl.getInstance();
        List<Order> orders = orderService.getUserOrders(user.getId());
        session.setAttribute("allUserOrders", orders);

        req.getRequestDispatcher("/account.jsp").forward(req, resp);
    }
}
