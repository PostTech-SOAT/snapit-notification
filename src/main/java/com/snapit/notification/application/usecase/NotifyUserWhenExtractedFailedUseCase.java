package com.snapit.notification.application.usecase;

import com.snapit.notification.application.interfaces.MailService;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import java.io.File;

import static com.snapit.notification.application.utils.HTMLUtils.readHTMLToString;

public class NotifyUserWhenExtractedFailedUseCase {

    private MailService mailService;

    public NotifyUserWhenExtractedFailedUseCase(MailService mailService) {
        this.mailService = mailService;
    }

    public void notifyUserWhenExtractedFailed(String email) {
        String notificationBody = readHTMLToString(new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "frames-extraction-error-email.html"));
        FileDataSource notificationImage = new FileDataSource("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "snapit.png");
        try {
            mailService.sendEmail(email, "Algo deu errado no processamento do seu vídeo no Snapit", notificationBody, notificationImage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
