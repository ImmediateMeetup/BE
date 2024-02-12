package com.example.immediatemeetupbe.domain.meeting.dto.request;

import java.time.LocalTime;
import java.util.List;
import lombok.Getter;

@Getter
public class MeetingUserTimeRequest {

    private List<LocalTime> timeList;
}
