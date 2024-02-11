package com.example.immediatemeetupbe.domain.meeting.controller;

import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingResponse;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<Void> registerMeeting(@RequestBody MeetingRegisterRequest meetingRegisterRequest) {
        meetingService.register(meetingRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> modifyMeeting(@RequestBody MeetingModifyRequest meetingModifyRequest) {
        meetingService.modify(meetingModifyRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponse> getMeetingInfo(@PathVariable("id") Long id) {
        MeetingResponse meetingResponse = meetingService.getMeetingInfoById(id);
        return ResponseEntity.ok().body(meetingResponse);
    }
}
