package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.AuthorizationToken;
import com.example.restaurant.model.User;
import com.example.restaurant.service.AuthorizationTokenService;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.impl.AuthorizationTokenServiceImpl;
import com.example.restaurant.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "authorizationServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("user");
        Long idValue = null;
        try {
            idValue = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOGGER.error("Can't parse Long from String", e);
            resp.sendRedirect("confirmEmailError.jsp");
        }
        String token = req.getParameter("token");

        if (idValue == null || token == null) {
            LOGGER.error("token or id is null");
            resp.sendRedirect("confirmEmailError.jsp");
        } else {
            AuthorizationTokenService authorizationTokenService = AuthorizationTokenServiceImpl.getInstance();
            Optional<AuthorizationToken> authorizationTokenOptional = authorizationTokenService.getUserToken(idValue);
            if (authorizationTokenOptional.isPresent()) {
                AuthorizationToken authorizationToken = authorizationTokenOptional.get();
                if (authorizationToken.getToken().equals(token)) {
                    UserService userService = UserServiceImpl.getInstance();
                    Optional<User> optionalUser = userService.getUser(idValue);
                    if (optionalUser.isPresent() && !optionalUser.get().getAuthorized()) {
                        userService.updateAuthorizationStatus(idValue);
                        resp.sendRedirect("confirmEmail.jsp");
                    } else {
                        resp.sendRedirect("confirmEmailError.jsp");
                    }
                } else {
                    LOGGER.error("tokens not equals");
                    resp.sendRedirect("confirmEmailError.jsp");
                }
            } else {
                resp.sendRedirect("confirmEmailError.jsp");
            }
        }
    }
}
