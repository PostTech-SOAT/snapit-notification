package com.snapit.notification.bdd;

import com.snapit.notification.application.interfaces.MailService;
import com.snapit.notification.application.usecase.NotifyUserWhenExtractedFailedUseCase;
import com.snapit.notification.application.utils.HTMLUtils;
import com.snapit.notification.framework.mail.JavaxMailService;
import com.snapit.notification.framework.rabbitmq.FramesExtractionQueueListener;
import com.snapit.notification.interfaceadaptors.event.FramesExtractionFailedEvent;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotifyUserStepDefinitions {
    private NotifyUserWhenExtractedFailedUseCase notifyUseCase;
    private FramesExtractionQueueListener listener;

    @Mock
    private MailService mailService;

    @Mock
    private JavaxMailService mockedMailService;

    private String userEmail;
    private String notificationBody;

    AutoCloseable openMocks;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @Dado("um usuário com e-mail {string}")
    public void umUsuarioComEmail(String email) {
        userEmail = email;
        notifyUseCase = new NotifyUserWhenExtractedFailedUseCase(mailService);
    }

    @Quando("o sistema detectar falha na extração de frames")
    @SneakyThrows
    public void oSistemaDetectarFalhaNaExtracaoDeFrames() {
        doNothing().when(mailService).sendEmail(any(), any(), any(), any(FileDataSource.class));
        notifyUseCase.notifyUserWhenExtractedFailed(userEmail);
    }

    @Então("um e-mail deve ser enviado ao usuário informando o erro")
    @SneakyThrows
    public void umEmailDeveSerEnviadoAoUsuarioInformandoOErro() {
        verify(mailService, times(1)).sendEmail(any(), any(), any(), any(FileDataSource.class));
    }

    @Quando("o sistema tentar enviar o e-mail e falhar")
    @SneakyThrows
    public void oSistemaTentarEnviarOEmailEFalhar() {
        doThrow(new MessagingException()).when(mailService).sendEmail(any(), any(), any(), any(FileDataSource.class));
    }

    @Então("uma exceção deve ser lançada")
    public void umaExcecaoDeveSerLancada() {
        assertThrows(RuntimeException.class, () -> notifyUseCase.notifyUserWhenExtractedFailed(userEmail));
    }

    @Dado("um arquivo HTML de notificação de erro")
    public void umArquivoHTMLDeNotificacaoDeErro() {
        File file = new File("src/main/resources/frames-extraction-error-email.html");
        assertTrue(file.exists(), "O arquivo HTML de notificação deve existir");
    }

    @Quando("o conteúdo for lido como string")
    @SneakyThrows
    public void oConteudoForLidoComoString() {
        notificationBody = HTMLUtils.readHTMLToString(new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "frames-extraction-error-email.html"));
    }

    @Então("o HTML lido deve corresponder ao esperado")
    public void oHtmlLidoDeveCorresponderAoEsperado() {
        String expectedHtml = "<html>" +
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

        assertEquals(expectedHtml, notificationBody);
    }

    @Dado("um evento de falha de extração para o arquivo {string} e usuário {string}")
    public void umEventoDeFalhaDeExtracaoParaOArquivoEUsuario(String filename, String email) {
        FramesExtractionFailedEvent event = new FramesExtractionFailedEvent();
        event.setFilename(filename);
        event.setUserEmail(email);

        listener = new FramesExtractionQueueListener(mockedMailService);
    }

    @Quando("o sistema receber a mensagem da fila")
    @SneakyThrows
    public void oSistemaReceberAMensagemDaFila() {
        FramesExtractionFailedEvent event = new FramesExtractionFailedEvent();
        event.setFilename("firstVideo.mp4");
        event.setUserEmail(userEmail);

        doNothing().when(mockedMailService).sendEmail(any(), any(), any(), any(FileDataSource.class));

        listener.receiveMessage(event);
    }

    @Então("um e-mail de falha deve ser enviado ao usuário")
    @SneakyThrows
    public void umEmailDeFalhaDeveSerEnviadoAoUsuario() {
        verify(mockedMailService, times(1)).sendEmail(any(), any(), any(), any(FileDataSource.class));
    }
}
