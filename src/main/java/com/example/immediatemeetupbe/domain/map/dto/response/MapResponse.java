package com.example.immediatemeetupbe.domain.map.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
public class MapResponse {

    private Long memberId;
    private Long meetingId;
    private Long latitude;
    private Long longitude;

}
