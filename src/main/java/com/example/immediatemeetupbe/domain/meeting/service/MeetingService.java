package com.example.immediatemeetupbe.domain.meeting.service;

import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingResponse;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.NO_EXIST_MEETING;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Transactional
    public void register(MeetingRegisterRequest meetingRegisterRequest) {
        meetingRepository.save(meetingRegisterRequest.toEntity());
    }

    @Transactional
    public void modify(MeetingModifyRequest meetingModifyRequest) {
        if (meetingRepository.existsById(meetingModifyRequest.getId())) {
            throw new BaseException(NO_EXIST_MEETING.getMessage());
        }
        Meeting meeting = meetingRepository.getById(meetingModifyRequest.getId());
        meeting.update(meetingModifyRequest.getTitle(), meetingModifyRequest.getContent(), meetingModifyRequest.getFirstDay(), meetingModifyRequest.getLastDay());
    }

    @Transactional
    public MeetingResponse getMeetingInfoById(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BaseException(NO_EXIST_MEETING.getMessage());
        }
        Meeting meeting = meetingRepository.getById(id);
        return MeetingResponse.from(meeting);
    }

    @Transactional
    public Meeting getMeetingInfo(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BaseException(NO_EXIST_MEETING.getMessage());
        }
        return meetingRepository.getById(id);
    }

    @Transactional
    public void delete(Long id) {
        if (meetingRepository.existsById(id)) {
            throw new BaseException(NO_EXIST_MEETING.getMessage());
        }

        Meeting meeting = meetingRepository.getById(id);
        meetingRepository.delete(meeting);
    }
}
