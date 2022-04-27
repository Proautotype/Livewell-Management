/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email;

import java.util.Properties;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class emailService {

    public Service<Boolean> sendSingle(String from, String to, String r_name, String host) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        // Recipient's email ID needs to be mentioned.
                        String to = "destinationemail@gmail.com";
                        // Sender's email ID needs to be mentioned
                        String from = "fromemail@gmail.com";

                        final String username = "Fred waters";//change accordingly
                        final String password = "chrishcts";//change accordingly
                        // Assuming you are sending email through relay.jangosmtp.net
                        String host = "relay.jangosmtp.net";

                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", host);
                        props.put("mail.smtp.port", "25");
                        // Get the Session object.
                        Session session = Session.getInstance(props,
                                new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        try {
                            // Create a default MimeMessage object.
                            Message message = new MimeMessage(session);
                            // Set From: header field of the header.
                            message.setFrom(new InternetAddress(from));
                            // Set To: header field of the header.
                            message.setRecipients(Message.RecipientType.TO,
                                    InternetAddress.parse(to));
                            // Set Subject: header field
                            message.setSubject("Testing Subject");
                            // Now set the actual message
                            message.setText("Hello, this is sample for to check send "
                                    + "email using JavaMailAPI ");
                            // Send message
                            Transport.send(message);
                            System.out.println("Sent message successfully....");
                        } catch (Exception e) {
                        }
                        return false;
                    }
                };
            }
        };
        return service;
    }

    class oneEmail {

        public void sendSingle(String to, String from, String msg) {
            // Recipient's email ID needs to be mentioned.
             to = "otuagyei2016@gmail.com";
            // Sender's email ID needs to be mentioned
             from = "winstyngyen@gmail.com";

            final String username = "Fred Waters";//change accordingly
            final String password = "chrishcts";//change accordingly
            // Assuming you are sending email through relay.jangosmtp.net
            String host = "www.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");
            // Get the Session object.
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);
                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));
                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                // Set Subject: header field
                message.setSubject("Testing Subject");
                // Now set the actual message
                message.setText(msg);
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
