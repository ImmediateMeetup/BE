package com.example.immediatemeetupbe.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailConfirmResponse {
    private String message;

    public static EmailConfirmResponse from(String message) {
        return EmailConfirmResponse.builder()
                .message(message)
                .build();
    }
}
