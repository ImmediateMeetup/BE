package com.example.immediatemeetupbe.domain.notice.controller;

import com.example.immediatemeetupbe.domain.notice.dto.request.NoticeRegisterRequest;
import com.example.immediatemeetupbe.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public ResponseEntity<Void> registerNotice(@RequestBody NoticeRegisterRequest noticeRegisterRequest) {
        noticeService.register(noticeRegisterRequest);
        return ResponseEntity.ok().build();
    }
}
