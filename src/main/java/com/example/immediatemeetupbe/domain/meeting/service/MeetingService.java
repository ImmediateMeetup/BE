package com.example.immediatemeetupbe.domain.meeting.service;

import com.example.immediatemeetupbe.domain.meeting.dto.MeetingDto;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingListResponse;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingResponse;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meetingMember.entity.MeetingMember;
import com.example.immediatemeetupbe.domain.meetingMember.service.MeetingMemberService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.meetingMember.repository.MemberMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.NO_EXIST_MEETING;
import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.NO_EXIST_MEMBER;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingMemberService meetingMemberService;

    private final MeetingRepository meetingRepository;
    private final MemberMeetingRepository memberMeetingRepository;

    private final AuthUtil authUtil;

    @Transactional
    public void register(MeetingRegisterRequest meetingRegisterRequest) {
        // 방을 만든 사람
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.save(meetingRegisterRequest.toEntity());

        memberMeetingRepository.save(MeetingMember.builder()
                .meeting(meeting)
                .member(member)
                .build());
    }

    @Transactional
    public void modify(MeetingModifyRequest meetingModifyRequest) {
        Meeting meeting = meetingRepository.findById(meetingModifyRequest.getId())
                .orElseThrow(() -> new BaseException(NO_EXIST_MEETING.getMessage()));
        meeting.update(meetingModifyRequest.getTitle(), meetingModifyRequest.getContent(), meetingModifyRequest.getFirstDay(), meetingModifyRequest.getLastDay());
    }

    @Transactional
    public MeetingResponse getMeetingInfoById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new BaseException(NO_EXIST_MEETING.getMessage()));

        return MeetingResponse.from(meeting);
    }

    @Transactional
    public Meeting getMeetingInfo(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new BaseException(NO_EXIST_MEETING.getMessage()));
    }

    @Transactional
    public void delete(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new BaseException(NO_EXIST_MEETING.getMessage()));

        meetingRepository.delete(meeting);
    }

    @Transactional(readOnly = true)
    public MeetingListResponse getAllMeetings() {
        Member member = authUtil.getLoginMember();
        List<MeetingMember> meetingMemberList = member.getMeetingMemberList();

        List<MeetingDto> meetingDtoList = meetingMemberList.stream()
                .map(meetingMember -> MeetingDto.from(meetingMember.getMeeting()))
                .toList();

        return MeetingListResponse.builder()
                .meetings(meetingDtoList)
                .build();
    }
}
