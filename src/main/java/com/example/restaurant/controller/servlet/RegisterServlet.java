package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.impl.UserServiceImpl;
import com.example.restaurant.util.SendMail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        User userForRegister = User.createUser(email, password.toCharArray(), phoneNumber, name, Role.USER);
        Optional<User> optionalUser = userService.registration(userForRegister);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            SendMail.sendInvitationMail(user.getEmail(), user.getName());
            resp.sendRedirect("/home");
        } else {
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Not valid information! Check this out!');");
            out.println("location.href='/login';");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
