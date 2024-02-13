package com.example.immediatemeetupbe.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class MemberProfileResponse {
    private String email;
    private String name;
    private String image;
    private String phoneNumber;
    private String address;
}
