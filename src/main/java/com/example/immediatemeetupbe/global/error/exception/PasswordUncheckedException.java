package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.error.Error;

public class PasswordUncheckedException extends RuntimeException {

    public PasswordUncheckedException() {
        super(Error.PASSWORD_UNCHECK.getMessage());
    }
}
