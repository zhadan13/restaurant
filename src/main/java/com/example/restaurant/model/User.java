package com.example.restaurant.model;

import com.example.restaurant.constants.Role;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = -621743728221983352L;

    private Long id;
    private String email;
    private char[] password;
    private String phoneNumber;
    private String name;
    private Role role;
    private Boolean authorized;

    private User() {
    }

    private User(String email, char[] password, String phoneNumber, String name, Role role, Boolean authorized) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.role = role;
        this.authorized = authorized;
    }

    private User(Long id, String email, char[] password, String phoneNumber, String name, Role role, Boolean authorized) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.role = role;
        this.authorized = authorized;
    }

    public static User createUser() {
        return new User();
    }

    public static User createUser(String email, char[] password, String phoneNumber, String name, Role role, Boolean authorized) {
        return new User(email, password, phoneNumber, name, role, authorized);
    }

    public static User createUser(Long id, String email, char[] password, String phoneNumber, String name, Role role, Boolean authorized) {
        return new User(id, email, password, phoneNumber, name, role, authorized);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Arrays.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(name, user.name) && role == user.role && Objects.equals(authorized, user.authorized);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, email, phoneNumber, name, role, authorized);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "User { " +
                "id = " + id +
                ", email = '" + email + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", name = '" + name + '\'' +
                ", role = " + role +
                ", authorized = " + authorized +
                '}';
    }
}
