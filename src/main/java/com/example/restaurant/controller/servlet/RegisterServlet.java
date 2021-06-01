package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = UserServiceImpl.getInstance();
        User userForRegister = User.createUser(email, password.toCharArray(), phoneNumber, name, Role.USER, false);
        Optional<User> optionalUser = userService.registration(userForRegister);
        if (optionalUser.isPresent()) {
            resp.sendRedirect("login");
        } else {
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Not valid information or entered email/phone has been already registered! " +
                    "Check this out! " +
                    "(Password must contain at least 8 chars: one digit, one letter lowercase and one letter uppercase)');");
            out.println("location.href='register';");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
