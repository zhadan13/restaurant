package com.example.restaurant.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class SendMail {
    private static final Logger LOGGER = LogManager.getLogger(SendMail.class);

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_PATH = "/mail.properties";
    private static final String MAIL;
    private static final String PASSWORD;
    private static final Session SESSION;

    private SendMail() {

    }

    static {
        PROPERTIES.put("mail.smtp.host", "smtp.mail.yahoo.com");
        PROPERTIES.put("mail.smtp.port", "587");
        PROPERTIES.put("mail.smtp.auth", "true");
        PROPERTIES.put("mail.smtp.starttls.enable", "true");
        MAIL = "artem21zh@yahoo.com";
        PASSWORD = "pxiceamrbrtdxjix";
        SESSION = Session.getInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL, PASSWORD);
            }
        });
    }

    public static void sendVerificationMail(final String to, final Long userId, final String userName, final String token) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Welcome to Restaurant!");
            String text = "<h1>" + "Hello " + userName + "! " + message.getSubject() + "</h1>" +
                    "<p>" +
                    "One more thing... " +
                    "Please confirm your email address by going by link: " +
                    "</p>" +
                    "<a href='http://localhost:8080/authorization?user=" + userId + "&token=" + token + "''> Restaurant &#183; Confirm email address</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send email to user", e);
        }
    }

    public static void sendInvitationMail(final String to, final String name) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Welcome to Restaurant!");
            String text = "<h1>" + "Hello " + name + "! " + message.getSubject() + "</h1>" +
                    "<p>" +
                    "Thank you for registering in our restaurant chain. " +
                    "Please place your first order by going to the menu by link: " +
                    "</p>" +
                    "<a href='http://localhost:8080/home'> Restaurant &#183; Menu</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send email to user", e);
        }
    }

    public static void sendOrderMail(final String to, final String name, final Long orderId) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Order successfully created");
            String text = "<h1>" + "Hello " + name + "! " + message.getSubject() + "</h1>" +
                    "<p>" +
                    "Your order in the restaurant chain has been successfully created and accepted for processing by the manager. " +
                    "<br><strong>" + "Order number: " + orderId + "</strong><br>" +
                    "You can always track and check the order in your personal account by the link: " +
                    "</p>" +
                    "<a href='http://localhost:8080/account'> Restaurant &#183; Account</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send email to user", e);
        }
    }
}
