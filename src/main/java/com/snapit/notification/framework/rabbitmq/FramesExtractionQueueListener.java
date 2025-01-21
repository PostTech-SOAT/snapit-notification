package com.snapit.notification.framework.rabbitmq;

import com.snapit.notification.framework.mail.JavaxMailService;
import com.snapit.notification.interfaceadaptors.controller.NotificationsController;
import com.snapit.notification.interfaceadaptors.event.FramesExtractionFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FramesExtractionQueueListener {

    private final JavaxMailService mailService;

    @RabbitListener(queues = "frames-extraction-failed-notification-queue")
    public void receiveMessage(FramesExtractionFailedEvent event) {
        NotificationsController controller = new NotificationsController();
        controller.notifyUserWhenExtractedFailed(event, mailService);
    }

}
