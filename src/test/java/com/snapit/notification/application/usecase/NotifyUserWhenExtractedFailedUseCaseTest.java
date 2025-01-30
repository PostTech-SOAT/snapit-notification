package com.snapit.notification.application.usecase;

import com.snapit.notification.application.interfaces.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotifyUserWhenExtractedFailedUseCaseTest {

    private NotifyUserWhenExtractedFailedUseCase useCase;

    @Mock
    private MailService mailService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        useCase = new NotifyUserWhenExtractedFailedUseCase(mailService);
    }

    @Test
    void shouldNotifyUserWhenExtractedFailed() throws MessagingException {
        doNothing().when(mailService).sendEmail(any(String.class), any(String.class), any(String.class), any(FileDataSource.class));

        useCase.notifyUserWhenExtractedFailed("email@test.com");

        InOrder inOrder = inOrder(mailService);
        inOrder.verify(mailService).sendEmail(any(String.class), any(String.class), any(String.class), any(FileDataSource.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldThrowExceptionWhenSendEmailFails() throws MessagingException {
        doThrow(new MessagingException()).when(mailService).sendEmail(any(String.class), any(String.class), any(String.class), any(FileDataSource.class));

        assertThrows(RuntimeException.class, () -> useCase.notifyUserWhenExtractedFailed("email@test.com"));
    }
}
