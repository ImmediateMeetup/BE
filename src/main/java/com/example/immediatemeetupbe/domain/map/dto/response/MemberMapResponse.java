package com.example.immediatemeetupbe.domain.map.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberMapResponse {

    private Long memberId;
    private Long meetingId;
    private Long latitude;
    private Long longitude;

}
