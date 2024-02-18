package com.example.immediatemeetupbe.domain.meetingMember.controller;

import com.example.immediatemeetupbe.domain.meetingMember.dto.request.MeetingMemberTimeRequest;
import com.example.immediatemeetupbe.domain.meetingMember.dto.response.MeetingMemberResponse;
import com.example.immediatemeetupbe.domain.meetingMember.service.MeetingMemberService;
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
@RequestMapping("/api/meet-up/meetingMember")
public class MeetingMemberController {

    private final MeetingMemberService meetingMemberService;

    @PostMapping("/{meeting_id}")
    public ResponseEntity<MeetingMemberResponse> registerUserTime(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody MeetingMemberTimeRequest meetingMemberTimeRequest) {
        return ResponseEntity.ok().body(
            meetingMemberService.registerMemberTime(meetingId, meetingMemberTimeRequest));
    }

    @PatchMapping("/{meeting_id}")
    public ResponseEntity<MeetingMemberResponse> updateMemberTime(
        @PathVariable("meeting_id") Long meetingId,
        @RequestBody MeetingMemberTimeRequest meetingMemberTimeRequest) {
        return ResponseEntity.ok().body(
            meetingMemberService.updateMemberTime(meetingId, meetingMemberTimeRequest));
    }

    @GetMapping("{meeting_id}")
    public ResponseEntity<MeetingMemberResponse> getTimeTable(
        @PathVariable("meeting_id") Long meetingId) {
        return ResponseEntity.ok().body(meetingMemberService.getTimeTable(meetingId));
    }

}
