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

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Comment> commentMemberList;

    @OneToMany(mappedBy = "meeting", orphanRemoval = true)
    private List<Comment> commentMeetingList;

    @Builder
    public MeetingMember(Member member, Meeting meeting, String timeZone) {
        this.member = member;
        this.meeting = meeting;
        this.timeZone = timeZone;
    }

    public void registerMemberTime(String timeZone) {
        this.timeZone = timeZone;
    }
}
