package com.example.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizationToken implements Serializable {
    private static final long serialVersionUID = 7309036786780438521L;

    private Long id;
    private Long userId;
    private String token;

    private AuthorizationToken() {

    }

    private AuthorizationToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    private AuthorizationToken(Long id, String token, Long userId) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    public static AuthorizationToken createToken() {
        return new AuthorizationToken();
    }

    public static AuthorizationToken createToken(String token, Long userId) {
        return new AuthorizationToken(token, userId);
    }

    public static AuthorizationToken createToken(Long id, Long userId, String token) {
        return new AuthorizationToken(id, token, userId);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorizationToken that = (AuthorizationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, userId);
    }

    @Override
    public String toString() {
        return "AuthorizationToken { " +
                "id = " + id +
                ", token = '" + token + '\'' +
                ", userId = " + userId +
                '}';
    }
}
