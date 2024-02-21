package com.example.immediatemeetupbe.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // SUCCESS
    SUCCESS(HttpStatus.OK, "", "요청에 성공하였습니다."),

    // AUTH
    NO_AUTH_MEMBER(HttpStatus.UNAUTHORIZED, "A-001","권한이 없습니다."),
//    NO_EXIST_MEMBER(HttpStatus.NOT_FOUND, "A-002","ID에 해당하는 회원을 찾을 수 없습니다. ID: "),
    ERROR_GET_MEMBER(HttpStatus.UNAUTHORIZED, "A-002","로그인 멤버를 가져오는 중 오류가 발생했습니다."),

    // MEMBER
    NO_EXIST_MEMBER(HttpStatus.NOT_FOUND, "U-001","존재하지 않는 회원입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "U-002","이미 존재하는 이메일입니다."),
    PASSWORD_UNCHECK(HttpStatus.BAD_REQUEST, "U-003","비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "U-004","비밀번호가 틀렸습니다."),
    NOT_VALIDATE_EMAIL(HttpStatus.BAD_REQUEST, "U-005","유효하지 않는 이메일입니다."),

    // MEETING
    NO_EXIST_MEETING(HttpStatus.NOT_FOUND, "M-001","존재하지 않는 약속방 입니다."),
    ERROR_GET_MEETING(HttpStatus.INTERNAL_SERVER_ERROR, "M-002","약속정보를 불러오는 중 오류가 발생했습니다."),

    // COMMENT
    NO_EXIST_PARENT_COMMENT(HttpStatus.NOT_FOUND, "C-001","부모 댓글을 찾을 수 없습니다."),
    NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, "C-002","존재하지 않는 댓글 입니다"),

    // PARTICIPANT
    NO_EXIST_PARTICIPANT(HttpStatus.BAD_REQUEST, "P-001","미팅에 존재하는 참가자가 아닙니다."),
    NOT_HOST_OF_MEETING(HttpStatus.BAD_REQUEST, "P-002","약속방의 방장이 아닙니다."),
    ALREADY_INVITED(HttpStatus.BAD_REQUEST, "P-003","이미 초대된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}