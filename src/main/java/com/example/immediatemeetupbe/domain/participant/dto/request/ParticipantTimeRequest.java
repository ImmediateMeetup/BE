package com.example.immediatemeetupbe.domain.participant.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class ParticipantTimeRequest {

    private List<LocalDateTime> timeList;

    public Participant toEntity(Member member, Meeting meeting, String timeZone) {
        return Participant.builder()
            .member(member)
            .meeting(meeting)
            .timeZone(timeZone)
            .build();
    }
}
