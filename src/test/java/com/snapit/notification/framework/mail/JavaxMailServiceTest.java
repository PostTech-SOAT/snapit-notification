package com.snapit.notification.framework.mail;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Transport;
import java.io.File;

import static com.snapit.notification.application.utils.HTMLUtils.readHTMLToString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@SpringBootTest
class JavaxMailServiceTest {

    @Autowired
    private JavaxMailService javaxMailService;


    @Test
    void shouldSendEmail() throws Exception {
        String to = "snapit.postech@gmail.com";
        String notificationBody = readHTMLToString(new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "frames-extraction-error-email.html"));
        FileDataSource notificationImage = new FileDataSource("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "snapit.png");

        try (MockedStatic<Transport> mockedTransport = Mockito.mockStatic(Transport.class)) {
            javaxMailService.sendEmail(to, "Algo deu errado no processamento do seu vídeo no Snapit", notificationBody, notificationImage);

            mockedTransport.when(() -> Transport.send(any(Message.class))).thenAnswer(invocation -> null);
            mockedTransport.verify(() -> Transport.send(any()), times(1));
        }
    }

}
