package com.snapit.notification.interfaceadaptors.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FramesExtractionFailedEventTest {

    @Test
    void shouldCreateFramesExtractionFailedEvent() {
        FramesExtractionFailedEvent event = new FramesExtractionFailedEvent();
        event.setFilename("firstVideo.mp4");
        event.setUserEmail("email@test.com");

        assertEquals("firstVideo.mp4", event.getFilename());
        assertEquals("email@test.com", event.getUserEmail());
    }

}
