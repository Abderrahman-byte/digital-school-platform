package com.abderrahmane.elearning.authservice.helpers;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

// Note : I a don't know if creating one session for all messages is a good idea
// or I should create a session for each message

/** The main service for sending emails. */

@Component
public class MailService {
    private Environment environment;
    private Session session;

    @Autowired
    public MailService(ApplicationContext applicationContext) {
        Properties mailProperties = new Properties();
        String password, user;

        this.environment = applicationContext.getEnvironment();
        password = this.environment.getProperty("mail.smtp.password");
        user = this.environment.getProperty("mail.smtp.user");

        mailProperties.put("mail.smtp.host", this.environment.getProperty("mail.smtp.host"));
        mailProperties.put("mail.smtp.port", this.environment.getProperty("mail.smtp.port"));
        mailProperties.put("mail.smtp.ssl.trust", this.environment.getProperty("mail.smtp.host"));
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.ssl.enable", "true");

        session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    public void sendMail(String to, String subject, String content) {
        Message message = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        String from = this.environment.getProperty("mail.smtp.from");

        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            mimeBodyPart.setContent(content, "text/plain; charset=utf-8");
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception ex) {
            System.out.println("[sendMail-Exception] " + ex.getMessage());
        }
    }

    public void setDebug (boolean debug) {
        this.session.setDebug(debug);
    }
}
