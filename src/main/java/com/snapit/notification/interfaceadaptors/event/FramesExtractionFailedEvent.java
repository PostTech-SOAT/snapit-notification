package com.snapit.notification.interfaceadaptors.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class FramesExtractionFailedEvent implements Serializable {

    private String filename;

    private String userEmail;

}
