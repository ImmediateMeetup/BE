package com.example.immediatemeetupbe.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseExceptionStatus {

    // SUCCESS
    SUCCESS("요청에 성공하였습니다."),

    // MEMBER
    NO_AUTH_MEMBER("권한이 없습니다."),
    NO_EXIST_ENTITY("존재하지 않는 엔터티입니다."),
    EMAIL_ALREADY_EXIST("이미 존재하는 이메일입니다."),
    FOLLOW_NESTING("이미 팔로우 중입니다."),
    PASSWORD_UNCHECK("비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    NOT_VALIDATE_EMAIL("유효하지 않는 이메일입니다."),

    // MEETING
    NO_EXIST_MEETING("존재하지 않는 약속방 입니다.");

    private final String message;
}