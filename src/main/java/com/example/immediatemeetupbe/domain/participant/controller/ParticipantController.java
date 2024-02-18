package com.example.immediatemeetupbe.domain.participant.controller;

import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.participant.dto.response.TimeTableResponse;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/{meeting_id}")
    public ResponseEntity<ParticipantResponse> registerUserTime(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody ParticipantTimeRequest participantTimeRequest) {
        return ResponseEntity.ok().body(
            participantService.registerMemberTime(meetingId, participantTimeRequest));
    }

    @PatchMapping("/{meeting_id}")
    public ResponseEntity<ParticipantResponse> updateMemberTime(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody ParticipantTimeRequest participantTimeRequest) {
        return ResponseEntity.ok().body(
            participantService.updateMemberTime(meetingId, participantTimeRequest));
    }

    @GetMapping("{meeting_id}")
    public ResponseEntity<TimeTableResponse> getTimeTable(
        @PathVariable("meeting_id") Long meetingId) {
        return ResponseEntity.ok().body(participantService.getTimeTable(meetingId));
    }

}
