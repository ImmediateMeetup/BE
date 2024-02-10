package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.error.Error;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super(Error.WRONG_PASSWORD.getMessage());
    }
}
