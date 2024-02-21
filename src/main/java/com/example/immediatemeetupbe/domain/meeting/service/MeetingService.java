package com.example.immediatemeetupbe.domain.meeting.service;

import com.example.immediatemeetupbe.domain.meeting.dto.MeetingDto;
import com.example.immediatemeetupbe.domain.meeting.dto.request.ConfirmInviteRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingListResponse;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingResponse;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.member.repository.MemberRepository;
import com.example.immediatemeetupbe.global.redis.RedisService;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.participant.entity.host.Role;
import com.example.immediatemeetupbe.global.exception.BusinessException;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;

import com.example.immediatemeetupbe.domain.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final RedisService redisService;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;

    private final AuthUtil authUtil;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Transactional
    public void register(MeetingRegisterRequest meetingRegisterRequest) {
        // 방을 만든 사람
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.save(meetingRegisterRequest.toEntity());

        participantRepository.save(Participant.builder()
                .meeting(meeting)
                .member(member)
                .role(Role.HOST)
                .build());
    }

    @Transactional
    public void modify(MeetingModifyRequest meetingModifyRequest) {
        if (meetingRepository.existsById(meetingModifyRequest.getId())) {
            throw new BusinessException(NO_EXIST_MEETING);
        }
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(meetingModifyRequest.getId());

        if (findParticipantInfo(member, meeting).getRole() != Role.HOST) {
            throw new BusinessException(NOT_HOST_OF_MEETING);
        }

        meeting.update(meetingModifyRequest.getTitle(), meetingModifyRequest.getContent(),
                meetingModifyRequest.getFirstDay(), meetingModifyRequest.getLastDay());
    }

    @Transactional
    public MeetingResponse getMeetingInfoById(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BusinessException(NO_EXIST_MEETING);
        }
        Meeting meeting = meetingRepository.getById(id);
        return MeetingResponse.from(meeting);
    }

    @Transactional
    public Meeting getMeetingInfo(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BusinessException(NO_EXIST_MEETING);
        }
        return meetingRepository.getById(id);
    }

    @Transactional
    public void delete(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BusinessException(NO_EXIST_MEETING);
        }
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.getById(id);

        if (findParticipantInfo(member, meeting).getRole() != Role.HOST) {
            throw new BusinessException(NOT_HOST_OF_MEETING);
        }

        meetingRepository.delete(meeting);
    }

    @Transactional(readOnly = true)
    public MeetingListResponse getAllMeetings() {
        Member member = authUtil.getLoginMember();
        List<Participant> participantList = member.getParticipantList();

        List<MeetingDto> meetingDtoList = participantList.stream()
                .map(meetingMember -> MeetingDto.from(meetingMember.getMeeting()))
                .toList();

        return MeetingListResponse.builder()
                .meetings(meetingDtoList)
                .build();
    }

    @Transactional
    public void inviteMember(Long meetingId, Long memberId) {
        Member host = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BusinessException(NO_EXIST_MEETING));

        if (findParticipantInfo(host, meeting).getRole() != Role.HOST) {
            throw new BusinessException(NOT_HOST_OF_MEETING);
        }

        Member invitedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(NO_EXIST_MEMBER));

        if (participantRepository.existsByMemberAndMeeting(invitedMember, meeting)) {
            throw new BusinessException(ALREADY_INVITED);
        }

        redisService.addMeetingToList(AUTH_CODE_PREFIX + memberId, meeting);
    }

    @Transactional(readOnly = true)
    public MeetingListResponse getAllInviteInfo() {
        Member member = authUtil.getLoginMember();
        List<Meeting> meetingList = redisService.getMeetingValues(AUTH_CODE_PREFIX + member.getId());

        List<MeetingDto> meetingDtoList = meetingList.stream()
                .map(MeetingDto::from)
                .collect(Collectors.toList());

        return MeetingListResponse.builder()
                .meetings(meetingDtoList)
                .build();
    }

    @Transactional
    public void acceptInvite(ConfirmInviteRequest request) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new BusinessException(NO_EXIST_MEETING));

        if(request.getStatus() != ConfirmInviteRequest.ConfirmationStatus.ACCEPTED) {
            redisService.deleteMeetingValue(AUTH_CODE_PREFIX + member.getId());
            return;
        }

        participantRepository.save(Participant.builder()
                .meeting(meeting)
                .member(member)
                .role(Role.MEMBER)
                .build());
        redisService.deleteMeetingValue(AUTH_CODE_PREFIX + member.getId());
    }

    @Transactional
    public Participant findParticipantInfo(Member member, Meeting meeting) {
        return participantRepository.findByMemberAndMeeting(member,
                        meeting)
                .orElseThrow(() -> new BusinessException(NO_EXIST_PARTICIPANT));
    }
}