package com.example.immediatemeetupbe.domain.map.service;

import com.example.immediatemeetupbe.domain.map.dto.SubwayDto;
import com.example.immediatemeetupbe.domain.map.dto.request.MapRegisterRequest;
import com.example.immediatemeetupbe.domain.map.dto.response.MapResponse;
import com.example.immediatemeetupbe.domain.map.dto.response.MemberMapResponse;
import com.example.immediatemeetupbe.domain.map.vo.Point;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import java.io.IOException;
import java.util.Comparator;
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
    private final SubwayLocationApi subwayLocationApi;
    
    @Transactional
    public MemberMapResponse updateUserLocation(Long meetingId,
        MapRegisterRequest mapRegisterRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(meetingId);
        Participant participant = participantService.findParticipantInfo(member,
            meeting);
        participant.registerLocation(mapRegisterRequest.getLatitude().getLatitude(),
            mapRegisterRequest.getLongitude().getLongitude());
        return MemberMapResponse.builder().memberId(member.getId()).meetingId(meeting.getId())
            .latitude(participant.getLatitude()).longitude(
                participant.getLongitude()).build();
    }

    public MapResponse getCalculatePoint(Long meetingId) {
        List<Participant> participantList = participantService.getAllParticipantByMeetingId(
            meetingId);
        List<Point> arrays = graham.calculate(participantList);
        Point point = calculateMiddlePoint(arrays);
        List<SubwayDto> subwayDtoList;
        try {
            subwayDtoList = subwayLocationApi.getAllSubway();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SubwayDto subwayDto = calculateNearSubway(point, subwayDtoList);
        return MapResponse.builder().subwayId(subwayDto.getSTATN_ID())
            .subwayName(subwayDto.getSTATN_NM()).route(subwayDto.getROUTE())
            .longitude(subwayDto.getCRDNT_X()).latitude(subwayDto.getCRDNT_Y()).build();
    }

    private SubwayDto calculateNearSubway(Point point, List<SubwayDto> subwayDtoList) {
        return subwayDtoList.stream()
            .min(Comparator.comparingDouble(
                subway -> getDistance(point.getX(), point.getY(), subway)))
            .orElse(null);
    }

    private double getDistance(double x, double y, SubwayDto subway) {
        double distanceX = x - subway.getCRDNT_X();
        double distanceY = y - subway.getCRDNT_Y();
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
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
