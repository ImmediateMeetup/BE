package com.example.immediatemeetupbe.domain.meeting.dto.response;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.entity.Status;
import com.example.immediatemeetupbe.domain.notice.entity.Notice;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingResponse {

    private Long id;
    private String title;
    private String content;
    private String firstDay;
    private String lastDay;
    private String place;
    private String timeZone;
    private Status status;
    private List<Comment> comments;
    private List<Notice> notices;

    public MeetingResponse(Long id, String title, String content, String firstDay, String lastDay, String place, String timeZone, List<Comment> comments, List<Notice> notices, Status status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.place = place;
        this.timeZone = timeZone;
        this.status = status;
        this.comments = comments;
        this.notices = notices;
    }

    public static MeetingResponse from(Meeting meeting) {
        return new MeetingResponse(meeting.getId(), meeting.getTitle(), meeting.getContent(), meeting.getFirstDay(), meeting.getLastDay(), meeting.getPlace(), meeting.getTimeZone(), meeting.getComments(), meeting.getNotices(), meeting.getStatus());
    }
}
