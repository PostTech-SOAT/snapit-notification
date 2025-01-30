package com.snapit.notification.framework.mail;

import com.snapit.notification.application.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
@RequiredArgsConstructor
public class JavaxMailService implements MailService {

    @Value("${mail.authentication.user}")
    private String user;

    private final Session session;

    @Override
    public void sendEmail(String to, String subject, String body, FileDataSource image) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(body, "text/html; charset=UTF-8");
        multipart.addBodyPart(htmlPart);

        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.setDataHandler(new DataHandler(image));
        imagePart.setHeader("Content-ID", "<snapit>");
        multipart.addBodyPart(imagePart);

        message.setContent(multipart);
        Transport.send(message);
    }

}
