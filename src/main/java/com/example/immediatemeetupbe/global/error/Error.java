package com.example.immediatemeetupbe.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {

    NO_AUTH_MEMBER("권한이 없습니다."),
    NO_EXIST_ENTITY("존재하지 않는 엔터티입니다."),
    EMAIL_ALREADY_EXIST("이미 존재하는 이메일입니다."),
    FOLLOW_NESTING("이미 팔로우 중입니다."),
    PASSWORD_UNCHECK("비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.");

    private final String message;
}