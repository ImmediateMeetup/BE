package com.example.immediatemeetupbe.domain.notice.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRegisterRequest {
    private Long meetingId;
    private String title;
    private String content;

    public Notice toEntity(Member member, Meeting meeting) {
        return Notice.builder()
                .title(title)
                .content(content)
                .member(member)
                .meeting(meeting)
                .build();
    }
}
