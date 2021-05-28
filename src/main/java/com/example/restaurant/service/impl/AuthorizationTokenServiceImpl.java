package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.AuthorizationTokenDAO;
import com.example.restaurant.db.dao.impl.AuthorizationTokenDAOImpl;
import com.example.restaurant.model.AuthorizationToken;
import com.example.restaurant.service.AuthorizationTokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class AuthorizationTokenServiceImpl implements AuthorizationTokenService {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationTokenServiceImpl.class);

    private static AuthorizationTokenServiceImpl INSTANCE;

    private AuthorizationTokenServiceImpl() {

    }

    public static AuthorizationTokenServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthorizationTokenServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthorizationTokenServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<AuthorizationToken> saveToken(AuthorizationToken authorizationToken) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.save(authorizationToken);
    }

    @Override
    public boolean deleteToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.delete(id);
    }

    @Override
    public boolean updateToken(AuthorizationToken authorizationToken) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.update(authorizationToken);
    }

    @Override
    public Optional<AuthorizationToken> getToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.get(id);
    }

    @Override
    public List<AuthorizationToken> getAllTokens() {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.getAll();
    }

    @Override
    public Optional<AuthorizationToken> getUserToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.getByUserId(id);
    }
}
