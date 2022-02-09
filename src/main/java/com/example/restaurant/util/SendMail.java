package com.example.restaurant.util;

import com.example.restaurant.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.example.restaurant.constants.Util.APPLICATION_NAME;

/**
 * Util class for sending mail to user.
 *
 * @author Zhadan Artem
 */

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
        PROPERTIES.put("mail.smtp.host", "host");
        PROPERTIES.put("mail.smtp.port", "port");
        PROPERTIES.put("mail.smtp.auth", "auth");
        PROPERTIES.put("mail.smtp.starttls.enable", "starttls");
        MAIL = "mail";
        PASSWORD = "password";
        SESSION = Session.getInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL, PASSWORD);
            }
        });
    }

    /**
     * Method sends verification email with authorization token to user.
     *
     * @param user  {@link User} recipient
     * @param token authorization token for current user
     * @see User
     */
    public static void sendVerificationMail(final User user, final String token) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Welcome to Diamond Restaurant!");
            String text = "<h1>" + "Hello " + user.getName() + "! " + message.getSubject() + "</h1>" + "<p>" + "One more thing... " + "Please confirm your email address by going by following link: " + "</p>" + "<a href='http://localhost:8080" + APPLICATION_NAME + "/authorization?user=" + user.getId() + "&token=" + token + "''> Diamond Restaurant &#183; Confirm email address</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send email to user", e);
        }
    }

    /**
     * Method sends invitation email to user.
     *
     * @param user {@link User} recipient
     * @see User
     */
    public static void sendInvitationMail(final User user) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Welcome to Diamond Restaurant!");
            String text = "<h1>" + "Hello " + user.getName() + "! " + message.getSubject() + "</h1>" + "<p>" + "Thank you for registering in our restaurant chain. " + "Please place your first order by going to the menu by link: " + "</p>" + "<a href='http://localhost:8080" + APPLICATION_NAME + "/home'> Diamond Restaurant &#183; Menu</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send invitation email to user", e);
        }
    }

    /**
     * Method sends order email to user.
     *
     * @param user    {@link User} recipient
     * @param orderId identifier of user's order
     * @see User
     * @see com.example.restaurant.model.Order
     */
    public static void sendOrderMail(final User user, final Long orderId) {
        try {
            MimeMessage message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Order is successfully created!");
            String text = "<h1>" + "Hello " + user.getName() + "! " + message.getSubject() + "</h1>" + "<p>" + "Your order in Diamond Restaurant has been successfully created and accepted for processing by manager. " + "<br><strong>" + "Order number: " + orderId + "</strong><br>" + "You can always track and check the order in your personal account by the link: " + "</p>" + "<a href='http://localhost:8080" + APPLICATION_NAME + "/account'> Diamond Restaurant &#183; Account</a>";
            message.setContent(text, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Can't send order email to user", e);
        }
    }
}
