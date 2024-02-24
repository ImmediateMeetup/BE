package com.example.immediatemeetupbe.domain.map.service;

import com.example.immediatemeetupbe.domain.map.dto.request.MapRegisterRequest;
import com.example.immediatemeetupbe.domain.map.dto.response.MapResponse;
import com.example.immediatemeetupbe.domain.map.vo.Point;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapService {

    private final AuthUtil authUtil;
    private final MeetingService meetingService;
    private final ParticipantService participantService;
    private final Graham graham;

    @Transactional
    public MapResponse registerUserLocation(Long meetingId, MapRegisterRequest mapRegisterRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        Participant participant = participantService.findParticipantInfo(member,
            meeting);
        participant.registerLocation(mapRegisterRequest.getLatitude(),
            mapRegisterRequest.getLongitude());
        return MapResponse.builder().memberId(member.getId()).meetingId(meeting.getId())
            .latitude(participant.getLatitude()).longitude(
                participant.getLongitude()).build();
    }

    @Transactional
    public MapResponse modifyUserLocation(Long meetingId, MapRegisterRequest mapRegisterRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        Participant participant = participantService.findParticipantInfo(member,
            meeting);
        participant.registerLocation(mapRegisterRequest.getLatitude(),
            mapRegisterRequest.getLongitude());
        return MapResponse.builder().memberId(member.getId()).meetingId(meeting.getId())
            .latitude(participant.getLatitude()).longitude(
                participant.getLongitude()).build();
    }

    public MapResponse getCalculatePoint(Long meetingId) {
        List<Participant> participantList = participantService.getAllParticipantByMeetingId(
            meetingId);
        List<Point> arrays = graham.calculate(participantList);
        Point point = calculateMiddlePoint(arrays);
        return MapResponse.builder().meetingId(meetingId).longitude(point.getY())
            .latitude(point.getX()).build();
    }

    private Point calculateMiddlePoint(List<Point> arrays) {
        long sumX = 0;
        long sumY = 0;
        for (Point point : arrays) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / arrays.size(), sumY / arrays.size());
    }
}
