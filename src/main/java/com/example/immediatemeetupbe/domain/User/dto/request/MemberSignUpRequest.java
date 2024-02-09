package com.example.immediatemeetupbe.domain.User.dto.request;

import com.example.immediatemeetupbe.domain.User.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberSignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    private String checkedPassword;

    private String address;

    private String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }
}
