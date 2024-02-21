package com.example.immediatemeetupbe.domain.meeting.controller;

import com.example.immediatemeetupbe.domain.meeting.dto.request.ConfirmInviteRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import com.example.immediatemeetupbe.domain.meeting.dto.response.MeetingListResponse;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modifyMeeting(@PathVariable("id") Long id, @RequestBody MeetingModifyRequest meetingModifyRequest) {
        meetingService.modify(id, meetingModifyRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponse> getMeetingInfo(@PathVariable("id") Long id) {
        MeetingResponse meetingResponse = meetingService.getMeetingInfoById(id);
        return ResponseEntity.ok().body(meetingResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable("id") Long id) {
        meetingService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<MeetingListResponse> getAllMyMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @PostMapping("/invitations/{meetingId}/{memberId}")
    public ResponseEntity<Void> inviteMember(@PathVariable Long meetingId, @PathVariable Long memberId) {
        meetingService.inviteMember(meetingId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invitations")
    public ResponseEntity<MeetingListResponse> getAllInviteInfo() {
        return ResponseEntity.ok(meetingService.getAllInviteInfo());
    }

    @PostMapping("/invitations")
    public ResponseEntity<Void> acceptInvite(@RequestBody ConfirmInviteRequest confirmInviteRequest) {
        meetingService.acceptInvite(confirmInviteRequest);
        return ResponseEntity.ok().build();
    }
}
