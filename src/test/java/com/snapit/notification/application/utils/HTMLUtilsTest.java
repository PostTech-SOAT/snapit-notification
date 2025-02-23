package com.snapit.notification.application.utils;

import com.snapit.notification.application.utils.exception.HTMLToStringException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HTMLUtilsTest {

    @Test
    void shouldReadHTMLToString() {

        String notificationBody = HTMLUtils.readHTMLToString(new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "frames-extraction-error-email.html"));

        String expected =
                "<html>" +
                        "    <body><font size=\"3\">" +
                        "        <p>Olá,</p>" +
                        "        <p>Infelizmente, houve um erro ao processarmos o vídeo enviado no <strong>Snapit</strong>.</p>" +
                        "        <p>Sugerimos que verifique a plataforma para garantir que o arquivo tenha sido carregado corretamente.</p>" +
                        "        <p>Se necessário, você pode tentar o envio novamente ou ajustar o arquivo para continuar utilizando o serviço.</p>" +
                        "        <p>Agradecemos pela compreensão e, caso tenha mais dúvidas, estamos à disposição.</p>" +
                        "        <br>" +
                        "        <p>Atenciosamente,<br>" +
                        "            Equipe Snapit</p>" +
                        "        <img src='cid:snapit' width=\"308\" height=\"85\"/>" +
                        "        </font></body>" +
                        "</html>";

        assertEquals(expected, notificationBody);
    }

    @Test
    void shouldThrowHTMLToStringException() {

        assertThrows(HTMLToStringException.class, () -> HTMLUtils.readHTMLToString(new File("src")));

    }

}
