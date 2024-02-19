package com.example.immediatemeetupbe.domain.participant.dto.response;

import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantResponse {

    private Long memberId;

    private Long meetingId;

    private String timeZone;

    public ParticipantResponse(Long memberId, Long meetingId, String timeZone) {
        this.memberId = memberId;
        this.meetingId = meetingId;
        this.timeZone = timeZone;
    }

    public static ParticipantResponse from(Long memberId, Long meetingId, String timeZone) {
        return ParticipantResponse.builder().memberId(memberId).meetingId(meetingId)
            .timeZone(timeZone).build();
    }
}
