package com.example.immediatemeetupbe.domain.meeting.dto.response;

import com.example.immediatemeetupbe.domain.meeting.dto.MeetingDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingListResponse {

    List<MeetingDto> meetings;

    @Builder
    public MeetingListResponse(List<MeetingDto> meetings) {
        this.meetings = meetings;
    }
}
