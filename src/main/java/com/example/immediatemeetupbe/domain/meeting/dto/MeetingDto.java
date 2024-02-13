package com.example.immediatemeetupbe.domain.meeting.dto;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import lombok.Getter;

@Getter
public class MeetingDto {

    private Long id;
    private String title;
    private String content;
    private String firstDay;
    private String lastDay;
    private String place;
    private String timeZone;

    public MeetingDto(Long id, String title, String content, String firstDay, String lastDay, String place, String timeZone) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.place = place;
        this.timeZone = timeZone;
    }

    public static MeetingDto from(Meeting meeting) {
        return new MeetingDto(meeting.getId(), meeting.getTitle(), meeting.getContent(), meeting.getFirstDay(), meeting.getLastDay(), meeting.getPlace(), meeting.getTimeZone());
    }
}
