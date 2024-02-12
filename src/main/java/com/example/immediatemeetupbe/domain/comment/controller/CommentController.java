package com.example.immediatemeetupbe.domain.comment.controller;

import com.example.immediatemeetupbe.domain.comment.dto.request.CommentRegisterRequest;
import com.example.immediatemeetupbe.domain.comment.service.CommentService;
import com.example.immediatemeetupbe.domain.meeting.dto.request.MeetingRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> registerComment(@RequestBody CommentRegisterRequest commentRegisterRequest) {

        return ResponseEntity.ok().build();
    }
}
