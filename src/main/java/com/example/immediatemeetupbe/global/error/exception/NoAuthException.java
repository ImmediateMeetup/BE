package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;

public class NoAuthException extends RuntimeException{

    public NoAuthException() {
        super(BaseExceptionStatus.NO_AUTH_MEMBER.getMessage());
    }
}
