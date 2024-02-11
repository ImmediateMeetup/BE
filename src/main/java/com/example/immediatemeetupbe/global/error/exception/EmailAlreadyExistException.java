package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException() {
        super(BaseExceptionStatus.EMAIL_ALREADY_EXIST.getMessage());
    }
}
