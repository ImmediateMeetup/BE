package com.example.immediatemeetupbe.domain.comment.service;

import com.example.immediatemeetupbe.domain.comment.dto.request.CommentRegisterRequest;
import com.example.immediatemeetupbe.domain.comment.dto.request.CommentReplyRequest;
import com.example.immediatemeetupbe.domain.comment.dto.request.CommentUpdateRequest;
import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MeetingService meetingService;

    private final AuthUtil authUtil;

    @Transactional
    public void registerComment(CommentRegisterRequest commentRegisterRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(commentRegisterRequest.getMeeting());
        commentRepository.save(commentRegisterRequest.toEntity(member, meeting));
    }

    @Transactional
    public void registerReply(CommentReplyRequest commentReplyRequest) {
        Member member = authUtil.getLoginMember();
        Meeting meeting = meetingService.getMeetingInfo(commentReplyRequest.getMeeting());

        Comment parentComment = commentRepository.findById(commentReplyRequest.getParentId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PARENT_COMMENT.getMessage()));

        Comment comment = commentReplyRequest.toEntity(member, meeting, parentComment);
        parentComment.getChildComments().add(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentUpdateRequest.getId())
                .orElseThrow(() -> new BaseException(NO_EXIST_COMMENT.getMessage()));
        comment.update(commentUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BaseException(NO_EXIST_COMMENT.getMessage()));
        commentRepository.delete(comment);
    }
}
