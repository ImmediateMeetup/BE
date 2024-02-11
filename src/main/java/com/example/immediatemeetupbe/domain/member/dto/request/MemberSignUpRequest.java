package com.example.immediatemeetupbe.domain.member.dto.request;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.member.entity.auth.Authority;
import jakarta.validation.constraints.NotBlank;
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
                .authority(Authority.USER)
                .build();
    }
}
