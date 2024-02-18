package com.example.immediatemeetupbe.domain.participant.entity;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@IdClass(ParticipantId.class)
@Table(name = "participant")
public class Participant {

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
    public Participant(Member member, Meeting meeting, String timeZone) {
        this.member = member;
        this.meeting = meeting;
        this.timeZone = timeZone;
    }

    public void registerMemberTime(String timeZone) {
        this.timeZone = timeZone;
    }
}
