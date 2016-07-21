package com.github.sytolk;

import java.io.File;
import java.util.List;

/**
 * User: stanimir
 * Date: 7/21/16
 * developer STANIMIR MARINOV
 */

public class MailContainer {

    private String sender;

    private String toRecipients;

    private String ccRecipients;

    private String bccRecipients;

    private String subject;

    private String body;

    private String contentType;

    private List<File> attachments;

    public MailContainer() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addToRecipient(String recipient) {
        if (toRecipients == null) {
            toRecipients = recipient;
        } else {
            toRecipients += "," + recipient;
        }
    }

    public void addCcRecipient(String recipient) {
        if (ccRecipients == null) {
            ccRecipients = recipient;
        } else {
            ccRecipients += "," + recipient;
        }
    }

    public void addBccRecipient(String recipient) {
        if (bccRecipients == null) {
            bccRecipients = recipient;
        } else {
            bccRecipients += "," + recipient;
        }
    }

    public String getToRecipients() {
        return toRecipients;
    }

    public String getCcRecipients() {
        return ccRecipients;
    }

    public String getBccRecipients() {
        return bccRecipients;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }
}
