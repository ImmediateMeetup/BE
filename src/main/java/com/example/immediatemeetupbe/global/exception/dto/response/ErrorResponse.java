package com.example.immediatemeetupbe.global.exception.dto.response;

import com.example.immediatemeetupbe.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private HttpStatus status;                 // 에러 상태 코드
    private String divisionCode;        // 에러 구분 코드
    private String resultMsg;           // 에러 메시지

    @Builder
    protected ErrorResponse(final ErrorCode code) {
        this.resultMsg = code.getMessage();
        this.status = code.getHttpStatus();
        this.divisionCode = code.getErrorCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
