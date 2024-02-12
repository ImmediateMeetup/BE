package com.example.immediatemeetupbe.domain.memberMeeting.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.memberMeeting.dto.request.MeetingMemberTimeRequest;
import com.example.immediatemeetupbe.domain.memberMeeting.dto.response.MeetingMemberResponse;
import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMemberId;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.repository.MeetingRepository;
import com.example.immediatemeetupbe.repository.MemberMeetingRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingMemberService {

    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;
    private final AuthUtil authUtil;

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

        // 09~17
        String[] timeArray = meeting.getTimeZone().split("~");
        LocalTime firstTime = LocalTime.parse(timeArray[0]);
        LocalTime lastTime = LocalTime.parse(timeArray[1]);

        LocalDate firstDay = LocalDate.parse(meeting.getFirstDay());
        LocalDate lastDay = LocalDate.parse(meeting.getLastDay());

        LocalDateTime firstDateTime = LocalDateTime.of(firstDay, firstTime);
        LocalDateTime lastDateTime = LocalDateTime.of(lastDay, lastTime);
        Hashtable<LocalDateTime, Integer> timeSlot = new Hashtable<>();
        while (firstDateTime.isBefore(lastDateTime) || firstDateTime.isEqual(lastDateTime)) {
            timeSlot.put(firstDateTime, 0);
            firstDateTime = firstDateTime.plusMinutes(30);

        }
        List<MeetingMember> meetingMemberList = memberMeetingRepository.findAllByMeeting(
            meeting);

        meetingMemberList.forEach(meetingMember -> {
            Arrays.stream(meetingMember.getTimeZone().split("/"))
                .map(LocalDateTime::parse)
                .forEach(
                    timezone -> {
                        timeSlot.put(timezone, timeSlot.get(timezone) + 1);
                    });
        });
        return MeetingMemberResponse.from(timeSlot);
    }

    private static String changeTimeToString(MeetingMemberTimeRequest meetingMemberTimeRequest) {
        return meetingMemberTimeRequest.getTimeList().stream()
            .map(LocalDateTime::toString).collect(
                Collectors.joining("/"));
    }
}
