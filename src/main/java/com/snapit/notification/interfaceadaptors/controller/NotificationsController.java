package com.snapit.notification.interfaceadaptors.controller;

import com.snapit.notification.application.interfaces.MailService;
import com.snapit.notification.application.usecase.NotifyUserWhenExtractedFailedUseCase;
import com.snapit.notification.interfaceadaptors.event.FramesExtractionFailedEvent;

public class NotificationsController {

    public void notifyUserWhenExtractedFailed(FramesExtractionFailedEvent event, MailService mailService) {
        NotifyUserWhenExtractedFailedUseCase useCase = new NotifyUserWhenExtractedFailedUseCase(mailService);
        useCase.notifyUserWhenExtractedFailed(event.getUserEmail());
    }

}
