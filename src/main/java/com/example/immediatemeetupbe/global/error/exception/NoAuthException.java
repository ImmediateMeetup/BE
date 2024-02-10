package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.error.Error;

public class NoAuthException extends RuntimeException{

    public NoAuthException() {
        super(Error.NO_AUTH_MEMBER.getMessage());
    }
}
