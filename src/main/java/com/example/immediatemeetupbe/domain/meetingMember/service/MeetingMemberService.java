package com.example.immediatemeetupbe.domain.meetingMember.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.meetingMember.dto.request.MeetingMemberTimeRequest;
import com.example.immediatemeetupbe.domain.meetingMember.dto.response.MeetingMemberResponse;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMemberId;
import com.example.immediatemeetupbe.domain.meetingMember.valueObject.MeetingTime;
import com.example.immediatemeetupbe.domain.meetingMember.valueObject.TimeTable;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.repository.MeetingMemberRepository;
import com.example.immediatemeetupbe.repository.MeetingRepository;
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
    private final MeetingTime meetingTime;
    private final TimeTable timeTable;


    @Transactional
    public MeetingMemberResponse registerMemberTime(Long meetingId,
        MeetingMemberTimeRequest meetingMemberTimeRequest) {

        String timeZone = changeTimeToString(meetingMemberTimeRequest);
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);
        MeetingMember meetingMember = meetingMemberTimeRequest.toEntity(member, meeting, timeZone);
        memberMeetingRepository.save(meetingMember);

        return MeetingMemberResponse.from(meetingMember);
    }


    @Transactional
    public MeetingMemberResponse getTimeTable(Long meetingId) {
        Meeting meeting = meetingRepository.getById(meetingId);
        meetingTime.setMeetingTime(meeting.getTimeZone(), meeting.getFirstDay(),
            meeting.getLastDay());
        timeTable.setTimeTable(meetingTime.getFirstDateTime(), meetingTime.getLastDateTime());

        List<MeetingMember> meetingMemberList = memberMeetingRepository.findAllByMeeting(
            meeting);
        timeTable.calculateSchedule(meetingMemberList);

        return MeetingMemberResponse.from(timeTable);
    }

    private MeetingMember getMeetingMember(Member member, Meeting meeting) {
        return memberMeetingRepository.findById(
                new MeetingMemberId(member, meeting))
            .orElseThrow(() -> new BaseException(
                (BaseExceptionStatus.NO_EXIST_MEETING_OR_MEMBER.getMessage())));
    }


    public MeetingMemberResponse updateMemberTime(Long meetingId,
        MeetingMemberTimeRequest meetingMemberTimeRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);
        String timeZone = changeTimeToString(meetingMemberTimeRequest);
        getMeetingMember(member, meeting).registerMemberTime(timeZone);
    }

    private static String changeTimeToString(MeetingMemberTimeRequest meetingMemberTimeRequest) {
        return meetingMemberTimeRequest.getTimeList().stream()
            .map(LocalDateTime::toString).collect(
                Collectors.joining("/"));
    }


}
