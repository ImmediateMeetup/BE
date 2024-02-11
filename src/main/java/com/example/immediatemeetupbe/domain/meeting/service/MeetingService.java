package com.example.immediatemeetupbe.domain.meeting.service;

import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.global.error.exception.EmailAlreadyExistException;
import com.example.immediatemeetupbe.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
            // 수정
            throw new EmailAlreadyExistException();
        }

        Meeting meeting = meetingRepository.getById(meetingModifyRequest.getId());
        meeting.update(meetingModifyRequest.getTitle(), meetingModifyRequest.getContent(), meetingModifyRequest.getFirstDay(), meetingModifyRequest.getLastDay());
    }
}
