package com.example.immediatemeetupbe.domain.memberMeeting.dto.request;

import java.time.LocalTime;
import java.util.List;
import lombok.Getter;

@Getter
public class MeetingMemberTimeRequest {

    private List<LocalTime> timeList;
}
