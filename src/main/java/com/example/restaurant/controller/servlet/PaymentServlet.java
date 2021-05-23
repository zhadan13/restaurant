package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.impl.OrderServiceImpl;
import com.example.restaurant.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.restaurant.util.PaymentValidator.*;

@WebServlet(name = "payment", urlPatterns = "/payment")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String cardNumber = req.getParameter("cardNumber");
        String name = req.getParameter("name");
        String date = req.getParameter("expiration");
        String cvv = req.getParameter("cvv");

        if (validateCardNumber(cardNumber) && validateName(name) && validateExpirationDate(date) && validateCVV(cvv)) {
            Order order = (Order) session.getAttribute("order");
            OrderService orderService = OrderServiceImpl.getInstance();
            order.setStatus(OrderStatus.CONFIRMED);
            orderService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);
            session.setAttribute("order", order);
            ProductService productService = ProductServiceImpl.getInstance();
            order.getProducts().keySet().forEach(product -> productService.updateProductPopularity(product.getId(), product.getPopularity() + 1));
            resp.sendRedirect("/successOrder?orderId=" + order.getId());
        } else {
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Not valid information! Check this out!');");
            out.println("location.href='/payment';");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/payment.jsp").forward(req, resp);
    }
}
