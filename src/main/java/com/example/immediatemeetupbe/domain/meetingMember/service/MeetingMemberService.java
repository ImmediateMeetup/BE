package com.example.immediatemeetupbe.domain.meetingMember.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.meetingMember.dto.request.MeetingMemberTimeRequest;
import com.example.immediatemeetupbe.domain.meetingMember.dto.response.MeetingMemberResponse;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMemberId;
import com.example.immediatemeetupbe.domain.meetingMember.valueObject.MeetingTime;
import com.example.immediatemeetupbe.domain.meetingMember.valueObject.TimeTable;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.meetingMember.repository.MeetingMemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingMemberService {

    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository memberMeetingRepository;
    private final AuthUtil authUtil;
    private MeetingTime meetingTime;
    private TimeTable timeTable;


    @Transactional
    public MeetingMemberResponse registerMemberTime(Long meetingId,
                                                    MeetingMemberTimeRequest meetingMemberTimeRequest) {

        String timeZone = changeTimeToString(meetingMemberTimeRequest);

        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);
        memberMeetingRepository.save(meetingMemberTimeRequest.toEntity(member, meeting, timeZone));

        memberMeetingRepository.findById(
                        new MeetingMemberId(member, meeting)).get()
                .registerMemberTime(timeZone);

        meetingTime = new MeetingTime(meeting.getTimeZone(), meeting.getFirstDay(),
                meeting.getLastDay());

        timeTable = new TimeTable(meetingTime);

        List<MeetingMember> meetingMemberList = memberMeetingRepository.findAllByMeeting(
                meeting);
        timeTable.calculateSchedule(meetingMemberList);

        return MeetingMemberResponse.from(timeTable);
    }

    private static String changeTimeToString(MeetingMemberTimeRequest meetingMemberTimeRequest) {
        return meetingMemberTimeRequest.getTimeList().stream()
                .map(LocalDateTime::toString).collect(
                        Collectors.joining("/"));
    }
}
