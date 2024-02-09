package com.example.immediatemeetupbe.global.error.exception;

import com.example.immediatemeetupbe.global.error.Error;

public class FollowNestingException extends RuntimeException {

    public FollowNestingException() {
        super(Error.FOLLOW_NESTING.getMessage());
    }
}
