package com.example.immediatemeetupbe.domain.meeting.dto.request;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MeetingModifyRequest {
    private Long id;
    private String title;
    private String content;
    private String firstDay;
    private String lastDay;
}
