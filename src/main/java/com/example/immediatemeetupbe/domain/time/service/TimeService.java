package com.example.immediatemeetupbe.domain.time.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeResponse;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeTableResponse;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.vo.MeetingTime;
import com.example.immediatemeetupbe.domain.participant.vo.TimeTable;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final MeetingService meetingService;
    private final ParticipantService participantService;
    private final AuthUtil authUtil;
    private final MeetingTime meetingTime;
    private final TimeTable timeTable;

    @Transactional(readOnly = true)
    public TimeResponse getMemberTime(Long meetingId) {
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        Member member = authUtil.getLoginMember();
        Participant participant = participantService.findParticipantInfo(member, meeting);

        return TimeResponse.from(participant.getTimeZone().split("/"));
    }


    @Transactional
    public ParticipantResponse updateMemberTime(Long meetingId,
        ParticipantTimeRequest participantTimeRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        String timeZone = changeTimeToString(participantTimeRequest.getTimeList());

        Participant participant = participantService.findParticipantInfo(member, meeting);
        participant.registerMemberTime(timeZone);

        return ParticipantResponse.from(participant.getMember().getId(),
            participant.getMeeting().getId(), participant.getTimeZone());
    }


    private String changeTimeToString(List<LocalDateTime> memberTimeList) {
        return memberTimeList.stream()
            .map(LocalDateTime::toString).collect(
                Collectors.joining("/"));
    }

    @Transactional(readOnly = true)
    public TimeTableResponse getMeetingTimeTable(Long meetingId) {
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        meetingTime.setMeetingTime(meeting.getTimeZone(), meeting.getFirstDay(),
            meeting.getLastDay());
        timeTable.setTimeTable(meetingTime.getFirstDateTime(), meetingTime.getLastDateTime(),
            meetingTime.getFirstTime(), meetingTime.getLastTime());
        List<Participant> participantList = participantService.getAllParticipantByMeetingId(
            meetingId);

        timeTable.calculateSchedule(participantList);

        return TimeTableResponse.from(timeTable.getTimeTable());

    }
}
