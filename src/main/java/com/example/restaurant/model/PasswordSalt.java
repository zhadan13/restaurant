package com.example.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

public class PasswordSalt implements Serializable {
    private static final long serialVersionUID = 8309036786780438521L;

    private Long id;
    private String salt;
    private Long userId;

    private PasswordSalt() {

    }

    private PasswordSalt(String salt, Long userId) {
        this.salt = salt;
        this.userId = userId;
    }

    private PasswordSalt(Long id, String salt, Long userId) {
        this.id = id;
        this.salt = salt;
        this.userId = userId;
    }

    public static PasswordSalt createSalt() {
        return new PasswordSalt();
    }

    public static PasswordSalt createSalt(String salt, Long userId) {
        return new PasswordSalt(salt, userId);
    }

    public static PasswordSalt createSalt(Long id, String salt, Long userId) {
        return new PasswordSalt(id, salt, userId);
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
        PasswordSalt that = (PasswordSalt) o;
        return Objects.equals(id, that.id) && Objects.equals(salt, that.salt) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salt, userId);
    }

    @Override
    public String toString() {
        return "PasswordSalt { " +
                "id = " + id +
                ", salt = '" + salt + '\'' +
                ", userId = " + userId +
                '}';
    }
}
