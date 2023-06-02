package com.old.common.upload.event;

import org.springframework.context.ApplicationEvent;

/**
 * source List<UploadBo
 */
public class UploadEvent extends ApplicationEvent {


    public UploadEvent(Object source) {
        super(source);
    }
}
