package com.old.business.user.event;

import org.springframework.context.ApplicationEvent;

public class FileUploadEvent extends ApplicationEvent {
    public FileUploadEvent(Object source) {
        super(source);
    }
}
