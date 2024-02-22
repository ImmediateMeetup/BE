package com.example.immediatemeetupbe.domain.time.controller;

import com.example.immediatemeetupbe.domain.participant.dto.request.ParticipantTimeRequest;
import com.example.immediatemeetupbe.domain.participant.dto.response.ParticipantResponse;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeResponse;
import com.example.immediatemeetupbe.domain.time.dto.response.TimeTableResponse;
import com.example.immediatemeetupbe.domain.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/time")
public class TimeController {

    private final TimeService timeService;

    @PatchMapping("/{meeting_id}")
    public ResponseEntity<ParticipantResponse> updateMemberTime(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody ParticipantTimeRequest participantTimeRequest) {
        return ResponseEntity.ok().body(
            timeService.updateMemberTime(meetingId, participantTimeRequest));
    }

    @GetMapping("/{meeting_id}")
    public ResponseEntity<TimeResponse> getUserTime(
        @PathVariable("meeting_id") Long meetingId) {
        return ResponseEntity.ok().body(timeService.getMemberTime(meetingId));
    }

    @GetMapping("/all/{meeting_id}")
    public ResponseEntity<TimeTableResponse> getTimeTable(
        @PathVariable("meeting_id") Long meetingId) {
        return ResponseEntity.ok().body(timeService.getMeetingTimeTable(meetingId));
    }

}
