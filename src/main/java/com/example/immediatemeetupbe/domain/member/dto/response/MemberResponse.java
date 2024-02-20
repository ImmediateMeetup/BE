package com.example.immediatemeetupbe.domain.member.dto.response;

import com.example.immediatemeetupbe.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    List<MemberDto> members;
}
