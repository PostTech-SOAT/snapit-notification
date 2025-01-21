package com.snapit.notification.application.interfaces;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;

public interface MailService {
    void sendEmail(String to, String subject, String body, FileDataSource image) throws MessagingException;
}
