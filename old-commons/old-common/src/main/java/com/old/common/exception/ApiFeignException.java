package com.old.common.exception;

import com.old.common.base.BaseException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiFeignException extends BaseException {

    public ApiFeignException(String message) {
        super(message);
    }

    public ApiFeignException(Throwable cause) {
        super(cause);
    }

    public ApiFeignException(String message, Throwable cause) {
        super(message, cause);
    }
}
