package com.example.immediatemeetupbe.domain.memberMeeting.entity;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import java.io.Serializable;
import lombok.Builder;

public class MeetingMemberId implements Serializable {

    private final Member member;
    private final Meeting meeting;

    @Builder
    public MeetingMemberId(Member member, Meeting meeting) {
        this.member = member;
        this.meeting = meeting;
    }
}