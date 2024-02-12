package com.example.immediatemeetupbe.domain.comment.dto.request;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class CommentRegisterRequest {

    private String content;

    public Comment toEntity(Member member, Meeting meeting) {
        return Comment.builder()
                .content(content)
                .member(member)
                .meeting(meeting)
                .build();
    }
}
