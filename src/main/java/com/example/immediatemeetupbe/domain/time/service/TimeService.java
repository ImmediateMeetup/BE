package com.example.immediatemeetupbe.domain.time.service;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.NO_EXIST_PARTICIPANT;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeResponse;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeTableResponse;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.entity.ParticipantId;
import com.example.immediatemeetupbe.domain.participant.repository.ParticipantRepository;
import com.example.immediatemeetupbe.domain.participant.vo.MeetingTime;
import com.example.immediatemeetupbe.domain.participant.vo.TimeTable;
import com.example.immediatemeetupbe.global.exception.BusinessException;
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

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final AuthUtil authUtil;
    private final MeetingTime meetingTime;
    private final TimeTable timeTable;

    @Transactional(readOnly = true)
    public TimeResponse getMemberTime(Long meetingId) {
        Meeting meeting = meetingRepository.getById(meetingId);
        Member member = authUtil.getLoginMember();
        Participant participant = participantRepository.findByMemberAndMeeting(member, meeting)
            .orElseThrow(() -> new BusinessException(NO_EXIST_PARTICIPANT));

        return TimeResponse.from(participant.getTimeZone().split("/"));
    }


    @Transactional
    public ParticipantResponse updateMemberTime(Long meetingId,
        ParticipantTimeRequest participantTimeRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);
        String timeZone = changeTimeToString(participantTimeRequest.getTimeList());
        Participant participant = getMeetingMember(member, meeting);
        participant.registerMemberTime(timeZone);
        return ParticipantResponse.from(participant.getMember().getId(),
            participant.getMeeting().getId(), participant.getTimeZone());
    }

    private Participant getMeetingMember(Member member, Meeting meeting) {
        return participantRepository.findById(
                new ParticipantId(member, meeting))
            .orElseThrow(() -> new BusinessException(NO_EXIST_PARTICIPANT));
    }


    private static String changeTimeToString(List<LocalDateTime> memberTimeList) {
        return memberTimeList.stream()
            .map(LocalDateTime::toString).collect(
                Collectors.joining("/"));
    }

    @Transactional(readOnly = true)
    public TimeTableResponse getMeetingTimeTable(Long meetingId) {
        Meeting meeting = meetingRepository.getById(meetingId);
        meetingTime.setMeetingTime(meeting.getTimeZone(), meeting.getFirstDay(),
            meeting.getLastDay());
        timeTable.setTimeTable(meetingTime.getFirstDateTime(), meetingTime.getLastDateTime());
        List<Participant> participantList = participantRepository.findAllByMeeting(
            meeting);
        timeTable.calculateSchedule(participantList);

        return TimeTableResponse.from(timeTable.getTimeTable());

    }
}
