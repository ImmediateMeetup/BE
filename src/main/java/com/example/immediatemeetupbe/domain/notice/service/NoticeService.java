package com.example.immediatemeetupbe.domain.notice.service;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.notice.dto.request.NoticeModifyRequest;
import com.example.immediatemeetupbe.domain.notice.dto.request.NoticeRegisterRequest;
import com.example.immediatemeetupbe.domain.notice.entity.Notice;
import com.example.immediatemeetupbe.domain.notice.repository.NoticeRepository;
import com.example.immediatemeetupbe.domain.participant.entity.host.Role;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import com.example.immediatemeetupbe.global.exception.BusinessException;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantService participantService;

    private final AuthUtil authUtil;

    @Transactional
    public void register(NoticeRegisterRequest noticeRegisterRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingRepository.findById(noticeRegisterRequest.getMeetingId())
                .orElseThrow(() -> new BusinessException(NO_EXIST_MEETING));

        if (participantService.findParticipantInfo(member, meeting).getRole() != Role.HOST) {
            throw new BusinessException(NOT_HOST_OF_MEETING);
        }
        noticeRepository.save(noticeRegisterRequest.toEntity(member, meeting));
    }

    @Transactional
    public void modify(Long id, NoticeModifyRequest noticeModifyRequest) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NO_EXIST_NOTICE));
        Member member = authUtil.getLoginMember();

        if (participantService.findParticipantInfo(member, notice.getMeeting()).getRole() != Role.HOST) {
            throw new BusinessException(NOT_HOST_OF_MEETING);
        }

        notice.update(noticeModifyRequest.getTitle(), noticeModifyRequest.getContent());
    }
}
