package com.example.immediatemeetupbe.domain.member.dto.request;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.member.entity.auth.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "아이디를 입력해주세요")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&^])[A-Za-z\\d$@$!%*#?&^]{8,20}$")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&^])[A-Za-z\\d$@$!%*#?&^]{8,20}$")
    private String checkedPassword;

    private String address;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$")
    private String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .address(address)
                .phoneNumber(phoneNumber)
                .profileImage("https://github.com/Erichong7/Instagram_Project/assets/97429550/05958834-4e72-4e5c-9cad-652b5c6071cc")
                .authority(Authority.USER)
                .build();
    }
}
