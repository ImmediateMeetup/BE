package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.error.Error;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException() {
        super(Error.EMAIL_ALREADY_EXIST.getMessage());
    }
}
