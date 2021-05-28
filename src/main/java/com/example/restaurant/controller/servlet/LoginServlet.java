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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        Optional<User> optionalUser = userService.authorization(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getAuthorized()) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                if (user.getRole() == Role.USER) {
                    resp.sendRedirect("/home");
                } else {
                    resp.sendRedirect("/admin");
                }
            } else {
                PrintWriter out = resp.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Email address not confirmed! Please check your mailbox and confirm it!');");
                out.println("location.href='/login';");
                out.println("</script>");
            }
        } else {
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Email or Password incorrect! Check this out!');");
            out.println("location.href='/login';");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
