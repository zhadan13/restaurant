package com.example.restaurant.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentValidatorTest {
    @Test
    void validatePayment() {
        assertTrue(PaymentValidator.validatePayment("4444444444444444", "Name", "12/2030", "111"));
    }

    @Test
    void validatePaymentWrong() {
        assertFalse(PaymentValidator.validatePayment("0000444444444444", "Name", "122030", "111"));
    }

    @Test
    void validateCardNumber() {
        assertTrue(PaymentValidator.validateCardNumber("4444444444444444"));
    }

    @Test
    void validateCardNumberWrong() {
        assertFalse(PaymentValidator.validateCardNumber("9999454556566767"));
    }

    @Test
    void validateName() {
        assertTrue(PaymentValidator.validateName("Name"));
    }

    @Test
    void validateNameWrong() {
        assertFalse(PaymentValidator.validateName("     "));
    }

    @Test
    void validateExpirationDate() {
        assertTrue(PaymentValidator.validateExpirationDate("12/2030"));
    }

    @Test
    void validateExpirationDateWrong() {
        assertFalse(PaymentValidator.validateExpirationDate("12.30"));
    }

    @Test
    void validateCVV() {
        assertTrue(PaymentValidator.validateCVV("777"));
    }

    @Test
    void validateCVVWrong() {
        assertFalse(PaymentValidator.validateCVV("abc"));
    }
}
