package com.example.immediatemeetupbe.domain.memberMeeting.entity;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@IdClass(MeetingMemberId.class)
@Table(name = "meeting_member")
public class MeetingMember {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "time_zone")
    private String timeZone;

    @Builder
    public MeetingMember(Member member, Meeting meeting, String timeZone) {
        this.member = member;
        this.meeting = meeting;
        this.timeZone = timeZone;
    }

    public MeetingMember() {

    }

    public void registerMemberTime(String timeZone) {
        this.timeZone = timeZone;
    }
}
