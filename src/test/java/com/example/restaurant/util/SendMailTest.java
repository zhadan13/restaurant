package com.example.restaurant.util;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendMailTest {
    private final User user = User.createUser(1L, "user@mail.com", "123456".toCharArray(), "0660001111", "User", Role.USER, true);

    @Test
    void sendVerificationMail() {
        String token = "123456";
        try (MockedStatic<SendMail> mocked = mockStatic(SendMail.class)) {
            mocked.when(() -> SendMail.sendVerificationMail(user, token)).thenAnswer(invocationOnMock -> null);
            SendMail.sendVerificationMail(user, token);
            mocked.verify(() -> SendMail.sendVerificationMail(user, token), times(1));
        }
    }

    @Test
    void sendInvitationMail() {
        try (MockedStatic<SendMail> mocked = mockStatic(SendMail.class)) {
            mocked.when(() -> SendMail.sendInvitationMail(user)).thenAnswer(invocationOnMock -> null);
            SendMail.sendInvitationMail(user);
            mocked.verify(() -> SendMail.sendInvitationMail(user), times(1));
        }
    }

    @Test
    void sendOrderMail() {
        long id = 1L;
        try (MockedStatic<SendMail> mocked = mockStatic(SendMail.class)) {
            mocked.when(() -> SendMail.sendOrderMail(user, id)).thenAnswer(invocationOnMock -> null);
            SendMail.sendOrderMail(user, id);
            mocked.verify(() -> SendMail.sendOrderMail(user, id), times(1));
        }
    }
}
