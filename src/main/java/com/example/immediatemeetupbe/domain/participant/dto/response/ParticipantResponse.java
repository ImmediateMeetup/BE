package com.example.immediatemeetupbe.domain.participant.dto.response;

import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import lombok.Getter;

@Getter
public class ParticipantResponse {

    private final Participant participant;

    public ParticipantResponse(Participant participant) {
        this.participant = participant;
    }

    public static ParticipantResponse from(Participant participant) {
        return new ParticipantResponse(participant);
    }
}
