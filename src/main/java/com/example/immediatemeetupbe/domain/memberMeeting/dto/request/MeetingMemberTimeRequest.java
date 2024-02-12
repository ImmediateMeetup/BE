package com.example.immediatemeetupbe.domain.memberMeeting.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMember;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class MeetingMemberTimeRequest {

    private List<LocalDateTime> timeList;


    public MeetingMember toEntity(Member member, Meeting meeting, String timeZone) {
        return MeetingMember.builder().member(member).meeting(meeting).timeZone(timeZone).build();
    }
}
