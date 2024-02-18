package com.example.immediatemeetupbe.domain.meeting.dto.response;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
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
    private List<Comment> comments;

    public MeetingResponse(Long id, String title, String content, String firstDay, String lastDay, String place, String timeZone, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.place = place;
        this.timeZone = timeZone;
        this.comments = comments;
    }

    public static MeetingResponse from(Meeting meeting) {
        return new MeetingResponse(meeting.getId(), meeting.getTitle(), meeting.getContent(), meeting.getFirstDay(), meeting.getLastDay(), meeting.getPlace(), meeting.getTimeZone(), meeting.getComments());
    }
}
