package com.github.sytolk;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: stanimir
 * Date: 7/21/16
 * developer STANIMIR MARINOV
 */

public class MailManager {

    private MailContainer mailContainer;

    public MailManager(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }

    public void sendMail() throws MessagingException {

        final Properties properties = new Properties();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("mail_config.properties");
            properties.load(is);
        } catch (Exception e) {
            System.out.println("Can't read the properties file. Make sure mail_config.properties is in the CLASSPATH " + e.getMessage());
            return;
        }

        // Add user authentication if necessary
        Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("user"), properties.getProperty("password"));
            }
        });//null);
        MimeMessage message = new MimeMessage(mailSession);
        if (mailContainer.getToRecipients() != null && !mailContainer.getToRecipients().isEmpty()) {
            try {
                message.addRecipients(Message.RecipientType.TO, mailContainer.getToRecipients());
            } catch (Exception e) {
                System.out.println("Can't parse email/s:" + mailContainer.getToRecipients());
                return;
            }
        }
        if (mailContainer.getCcRecipients() != null) {
            try {
                message.addRecipients(Message.RecipientType.CC, mailContainer.getCcRecipients());
            } catch (Exception e) {
                System.out.println("Can't parse email/s:" + mailContainer.getCcRecipients());
                return;
            }
        }
        if (mailContainer.getBccRecipients() != null) {
            try {
                message.addRecipients(Message.RecipientType.BCC, mailContainer.getBccRecipients());
            } catch (Exception e) {
                System.out.println("Can't parse email/s:" + mailContainer.getBccRecipients());
                return;
            }
        }

        if (mailContainer.getSender() != null && mailContainer.getSender().length() > 0) {
            message.setFrom(new InternetAddress(mailContainer.getSender()));
            message.setReplyTo(new Address[]{new InternetAddress(mailContainer.getSender())});
        }

        message.setSubject(mailContainer.getSubject(), "UTF-8");


        if (mailContainer.getAttachments() == null || mailContainer.getAttachments().isEmpty()) {
            if (mailContainer.getContentType() != null)
                message.setContent(mailContainer.getBody(), mailContainer.getContentType() + "; charset=utf-8");
            else
                message.setText(mailContainer.getBody(), "UTF-8");
        } else { //attachment
            Multipart multipart = new MimeMultipart();

            // creates body part for the message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            if (mailContainer.getContentType() != null)
                messageBodyPart.setContent(mailContainer.getBody(), mailContainer.getContentType() + "; charset=utf-8");
            else
                messageBodyPart.setText(mailContainer.getBody(), "UTF-8");

            multipart.addBodyPart(messageBodyPart);

            // creates attach part for the message
            for (File file : mailContainer.getAttachments()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                attachPart.setFileName(file.getName());
                multipart.addBodyPart(attachPart);
            }

            message.setContent(multipart);
        }


        Transport.send(message);
    }
}
