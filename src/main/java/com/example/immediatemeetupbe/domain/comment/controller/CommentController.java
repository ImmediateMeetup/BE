package com.example.immediatemeetupbe.domain.comment.controller;

import com.example.immediatemeetupbe.domain.comment.dto.request.CommentRegisterRequest;
import com.example.immediatemeetupbe.domain.comment.dto.request.CommentReplyRequest;
import com.example.immediatemeetupbe.domain.comment.dto.request.CommentUpdateRequest;
import com.example.immediatemeetupbe.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Void> registerComment(@RequestBody CommentRegisterRequest commentRegisterRequest) {
        commentService.registerComment(commentRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> registerReply(@RequestBody CommentReplyRequest commentReplyRequest) {
        commentService.registerReply(commentReplyRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping()
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        commentService.update(commentUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
