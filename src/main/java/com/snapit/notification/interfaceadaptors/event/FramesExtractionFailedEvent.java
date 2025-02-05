package com.snapit.notification.interfaceadaptors.event;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FramesExtractionFailedEvent implements Serializable {

    private String filename;

    private String userEmail;

}
