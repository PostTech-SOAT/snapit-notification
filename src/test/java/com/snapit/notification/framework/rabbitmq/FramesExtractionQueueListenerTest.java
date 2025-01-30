package com.snapit.notification.framework.rabbitmq;

import com.snapit.notification.framework.mail.JavaxMailService;
import com.snapit.notification.interfaceadaptors.event.FramesExtractionFailedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class FramesExtractionQueueListenerTest {

    private FramesExtractionQueueListener listener;

    @Mock
    private JavaxMailService service;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        listener = new FramesExtractionQueueListener(service);
    }

    @Test
    void shouldReceiveMessage() throws MessagingException {
        FramesExtractionFailedEvent event = new FramesExtractionFailedEvent();
        event.setFilename("firstVideo.mp4");
        event.setUserEmail("email@test.com");

        listener.receiveMessage(event);

        verify(service).sendEmail(any(String.class), any(String.class), any(String.class), any(FileDataSource.class));
    }

}
