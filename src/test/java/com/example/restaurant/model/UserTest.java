package com.example.restaurant.model;

import static com.example.restaurant.constants.Role.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testEquals() {
        User user = User.createUser("user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        User user1 = User.createUser("user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        assertEquals(user, user1);
    }

    @Test
    void testNotEquals() {
        User user = User.createUser("user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        User user1 = User.createUser("user@mail1.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        assertNotEquals(user, user1);
    }

    @Test
    void testHashCodeEquals() {
        User user = User.createUser(1L, "user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        User user1 = User.createUser(1L, "user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        assertEquals(user.hashCode(), user1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        User user = User.createUser(1L, "user@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        User user1 = User.createUser(1L, "user1@mail.com", "123456Aa".toCharArray(), "01234567890", "User", USER, true);
        assertNotEquals(user.hashCode(), user1.hashCode());
    }
}
