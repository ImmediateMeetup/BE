package com.example.immediatemeetupbe.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMemberId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMeetingRepository extends JpaRepository<MeetingMember, MeetingMemberId> {

    List<MeetingMember> findAllByMeeting(Meeting meeting);
    List<MeetingMember> findAllByMember(Member member);
}
