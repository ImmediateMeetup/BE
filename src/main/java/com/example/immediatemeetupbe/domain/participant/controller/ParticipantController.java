package com.example.immediatemeetupbe.domain.participant.controller;

import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/participants/{meeting_id}")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping()
    public ResponseEntity<ParticipantResponse> participantMeeting(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody ParticipantTimeRequest participantTimeRequest) {
        return ResponseEntity.ok().body(
            participantService.participantMeeting(meetingId, participantTimeRequest));
    }


    @DeleteMapping()
    public ResponseEntity<Void> secedeMeeting(@PathVariable("meeting_id") Long meetingId) {
        participantService.secedeMeeting(meetingId);
        return ResponseEntity.ok().build();
    }
}
