package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.User;
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
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "order", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String address = req.getParameter("address");
        String time = req.getParameter("time");
        int paymentIndex = Integer.parseInt(req.getParameter("payment"));
        User user = (User) session.getAttribute("user");
        Double cost = (Double) session.getAttribute("totalPrice");
        Double deliveryPrice = (Double) session.getAttribute("deliveryPrice");
        Map<Product, Integer> bucket = (Map<Product, Integer>) session.getAttribute("bucket");

        time = time.replace('T', ' ');
        time = time.concat(":00");
        Timestamp timestamp = Timestamp.valueOf(time);
        Payment payment = Payment.CASH;
        if (paymentIndex == 2) {
            payment = Payment.CARD_ONLINE;
        }
        if (paymentIndex == 3) {
            payment = Payment.CARD_OFFLINE;
        }

        OrderService orderService = OrderServiceImpl.getInstance();
        Order orderForRegister = Order.createOrder(user.getId(), OrderStatus.ACCEPTED, address, payment, cost + deliveryPrice, timestamp, bucket);
        Optional<Order> optionalOrder = orderService.saveOrder(orderForRegister);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            session.setAttribute("order", order);
            if (payment == Payment.CARD_ONLINE) {
                resp.sendRedirect("/payment");
            } else {
                order.setStatus(OrderStatus.CONFIRMED);
                orderService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);
                ProductService productService = ProductServiceImpl.getInstance();
                order.getProducts().keySet().forEach(product -> productService.updateProductPopularity(product.getId(), product.getPopularity() + 1));
                session.setAttribute("order", order);
                resp.sendRedirect("/successOrder?orderId=" + order.getId());
            }
        } else {
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Not valid information! Check this out!');");
            out.println("location.href='/order';");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/order.jsp").forward(req, resp);
    }
}
