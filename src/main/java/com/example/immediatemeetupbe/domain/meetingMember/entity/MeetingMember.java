package com.example.immediatemeetupbe.domain.meetingMember.entity;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    @Column(name = "host")
    private Boolean host;


    @Builder
    public MeetingMember(Member member, Meeting meeting, String timeZone, Boolean host) {
        this.member = member;
        this.meeting = meeting;
        this.timeZone = timeZone;
        this.host = host;
    }

    public void registerMemberTime(String timeZone) {
        this.timeZone = timeZone;
    }

    public void revitalizeHost(Boolean host) {
        this.host = host;
    }

}
