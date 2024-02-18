package com.example.immediatemeetupbe.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditPasswordRequest {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "필수 항목입니다.")
    private String email;

    @NotBlank(message = "필수 항목입니다.")
    private String password;

    @NotBlank(message = "필수 항목입니다.")
    private String checkPassword;
}
