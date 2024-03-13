package com.example.immediatemeetupbe.domain.participant.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.entity.ParticipantId;

import java.util.List;
import java.util.Optional;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, ParticipantId> {

    List<Participant> findAllByMeeting(Meeting meeting);

    boolean existsByMemberAndMeeting(Member member, Meeting meeting);

    Optional<Participant> findByMemberAndMeeting(Member member, Meeting meeting);
}
