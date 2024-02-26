package com.example.immediatemeetupbe.domain.participant.entity;

import com.example.immediatemeetupbe.domain.map.vo.Latitude;

import com.example.immediatemeetupbe.domain.map.vo.Longitude;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.entity.host.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "latitude")
    private Long latitude;

    @Column(name = "longitude")
    private Long longitude;

    @Builder
    public Participant(Member member, Meeting meeting, String timeZone, Role role) {
        this.member = member;
        this.meeting = meeting;
        this.timeZone = timeZone;
        this.role = role;
    }

    public void registerMemberTime(String timeZone) {
        this.timeZone = timeZone;
    }

    public void registerLocation(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
