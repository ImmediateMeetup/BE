package com.example.immediatemeetupbe.domain.meeting.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import lombok.Getter;

@Getter

public class MeetingModifyRequest {
    private Long id;
    private String title;
    private String content;
    private String firstDay;
    private String lastDay;

    public Meeting toEntity(){
        return Meeting.builder()
                .title(title)
                .content(content)
                .firstDay(firstDay)
                .lastDay(lastDay)
                .build();
    }
}
