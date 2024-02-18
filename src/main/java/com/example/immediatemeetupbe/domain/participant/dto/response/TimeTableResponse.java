package com.example.immediatemeetupbe.domain.participant.dto.response;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import lombok.Getter;

@Getter
public class TimeTableResponse {

    private final LinkedHashMap<LocalDateTime, Integer> timeTable;

    public TimeTableResponse(LinkedHashMap<LocalDateTime, Integer> timeTable) {
        this.timeTable = timeTable;
    }

    public static TimeTableResponse from(LinkedHashMap<LocalDateTime, Integer> timeTable) {
        return new TimeTableResponse(timeTable);
    }
}
