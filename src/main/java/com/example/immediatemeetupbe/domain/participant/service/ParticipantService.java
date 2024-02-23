package com.example.immediatemeetupbe.domain.participant.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.participant.entity.ParticipantId;
import com.example.immediatemeetupbe.global.exception.BusinessException;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.domain.participant.repository.ParticipantRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final AuthUtil authUtil;


    @Transactional
    public ParticipantResponse participantMeeting(Long meetingId,
        ParticipantTimeRequest participantTimeRequest) {
        String timeZone = changeTimeToString(participantTimeRequest.getTimeList());
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);

        if (participantRepository.existsByMemberAndMeeting(member, meeting)) {
            Participant existParticipant = participantRepository.findByMemberAndMeeting(member,
                    meeting)
                .orElseThrow(() -> new BusinessException(NO_EXIST_PARTICIPANT));
            existParticipant.registerMemberTime(timeZone);

            return ParticipantResponse.from(existParticipant.getMember().getId(),
                existParticipant.getMeeting().getId(), existParticipant.getTimeZone());
        }

        Participant participant = participantTimeRequest.toEntity(member, meeting, timeZone);
        participantRepository.save(participant);
        return ParticipantResponse.from(participant.getMember().getId(),
            participant.getMeeting().getId(), participant.getTimeZone());
    }


    @Transactional
    public String secedeMeeting(Long meetingId) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingId);
        participantRepository.delete(findParticipantInfo(member, meeting));
        return "삭제 성공";
    }

    public List<Participant> getAllParticipantByMeetingId(Long meetingId) {
        return participantRepository.findAllByMeeting(meetingRepository.getById(meetingId));
    }

    private static String changeTimeToString(List<LocalDateTime> memberTimeList) {
        return memberTimeList.stream()
            .map(LocalDateTime::toString).collect(
                Collectors.joining("/"));
    }

    @Transactional
    public Participant findParticipantInfo(Member member, Meeting meeting) {
        return participantRepository.findByMemberAndMeeting(member,
                meeting)
            .orElseThrow(() -> new BusinessException(NO_EXIST_PARTICIPANT));
    }
}
