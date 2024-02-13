package com.example.immediatemeetupbe.domain.memberMeeting.dto.response;

import com.example.immediatemeetupbe.domain.memberMeeting.valueObject.TimeTable;
import lombok.Getter;

@Getter
public class MeetingMemberResponse {

    private final TimeTable timeTable;

    public MeetingMemberResponse(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public static MeetingMemberResponse from(TimeTable timeTable) {
        return new MeetingMemberResponse(timeTable);
    }
}
