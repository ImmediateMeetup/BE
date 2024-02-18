package com.example.immediatemeetupbe.domain.participant.entity;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantId implements Serializable {

    private Member member;
    private Meeting meeting;

    public ParticipantId(Member member, Meeting meeting) {
        this.member = member;
        this.meeting = meeting;
    }

}