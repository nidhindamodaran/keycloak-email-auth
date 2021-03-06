package com.nidhin.keycloak.email.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailService {

    public static void send(String to, String subject, String content) {

        // Sender's email ID needs to be mentioned
        String from = System.getenv("FROM_EMAIL");

        // Assuming you are sending email from through gmails smtp
        String host = System.getenv("SMTP_HOST");

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", System.getenv("SMTP_PORT"));
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(System.getenv("EMAIL_USERNAME"), System.getenv("EMAIL_PASSWORD"));

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            System.out.println("sending email....");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {

            mex.printStackTrace();

        }

    }

}