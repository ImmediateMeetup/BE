package com.example.immediatemeetupbe.domain.meeting.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingRegisterRequest {

    private String title;
    private String content;
    private String firstDay;
    private String lastDay;
    private String timeZone;


    public Meeting toEntity() {
        return Meeting.builder()
            .title(title)
            .content(content)
            .firstDay(firstDay)
            .lastDay(lastDay)
            .timeZone(timeZone)
            .build();
    }
}


