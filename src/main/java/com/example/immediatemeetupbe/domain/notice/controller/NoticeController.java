package com.example.immediatemeetupbe.domain.notice.controller;

import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingModifyRequest;
import com.example.immediatemeetupbe.domain.notice.dto.request.NoticeModifyRequest;
import com.example.immediatemeetupbe.domain.notice.dto.request.NoticeRegisterRequest;
import com.example.immediatemeetupbe.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        noticeService.register(noticeRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modifyNotice(@PathVariable("id") Long id, @RequestBody NoticeModifyRequest noticeModifyRequest) {
        noticeService.modify(id, noticeModifyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
