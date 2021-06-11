package com.example.restaurant.constants;

import org.junit.jupiter.api.Test;

import static com.example.restaurant.constants.Payment.CARD_OFFLINE;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void parsePayment() {
        assertEquals(CARD_OFFLINE, Payment.parsePayment("card_OFFLINE"));
    }

    @Test
    void parsePaymentWrong() {
        assertNull(Payment.parsePayment("no money"));
    }
}
