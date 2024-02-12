package com.example.immediatemeetupbe.domain.memberMeeting.dto.response;

import java.time.LocalDateTime;
import java.util.Hashtable;
import lombok.Getter;

@Getter
public class MeetingMemberResponse {

    private Hashtable<LocalDateTime, Integer> timeTable;

    public MeetingMemberResponse(Hashtable<LocalDateTime, Integer> timeTable) {
        this.timeTable = timeTable;
    }

    public static MeetingMemberResponse from(Hashtable<LocalDateTime, Integer> timeTable) {
        return new MeetingMemberResponse(timeTable);
    }
}
