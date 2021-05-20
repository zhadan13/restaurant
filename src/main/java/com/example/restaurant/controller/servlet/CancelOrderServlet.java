package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "cancelOrder", urlPatterns = "/cancelOrder")
public class CancelOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.removeAttribute("totalPrice");
        session.removeAttribute("productsInBucket");
        session.removeAttribute("bucket");
        Order order = (Order) session.getAttribute("order");
        OrderService orderService = OrderServiceImpl.getInstance();
        orderService.updateOrderStatus(order.getId(), OrderStatus.REJECTED);
        session.removeAttribute("order");

        resp.sendRedirect("/");
    }
}