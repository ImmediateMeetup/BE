package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.config.BaseExceptionStatus;

public class PasswordUncheckedException extends RuntimeException {

    public PasswordUncheckedException() {
        super(BaseExceptionStatus.PASSWORD_UNCHECK.getMessage());
    }
}
