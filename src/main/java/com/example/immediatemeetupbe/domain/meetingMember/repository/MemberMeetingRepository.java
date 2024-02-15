package com.example.immediatemeetupbe.domain.meetingMember.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMemberId;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberMeetingRepository extends JpaRepository<MeetingMember, MeetingMemberId> {

    List<MeetingMember> findAllByMeeting(Meeting meeting);
    List<MeetingMember> findAllByMember(Member member);
}
