package com.example.immediatemeetupbe.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMemberId;

import java.util.List;
import java.util.Optional;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, MeetingMemberId> {

    List<MeetingMember> findAllByMeeting(Meeting meeting);

    boolean existsByMember(Member member);

    MeetingMember getMeetingByMember(Member member);
}
