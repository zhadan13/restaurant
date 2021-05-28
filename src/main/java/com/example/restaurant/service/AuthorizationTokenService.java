package com.example.restaurant.service;

import com.example.restaurant.model.AuthorizationToken;

import java.util.List;
import java.util.Optional;

public interface AuthorizationTokenService {
    Optional<AuthorizationToken> saveToken(AuthorizationToken authorizationToken);

    boolean deleteToken(Long id);

    boolean updateToken(AuthorizationToken authorizationToken);

    Optional<AuthorizationToken> getToken(Long id);

    List<AuthorizationToken> getAllTokens();

    Optional<AuthorizationToken> getUserToken(Long id);
}
