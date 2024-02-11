package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;

public class FollowNestingException extends RuntimeException {

    public FollowNestingException() {
        super(BaseExceptionStatus.FOLLOW_NESTING.getMessage());
    }
}
