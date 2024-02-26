package com.example.immediatemeetupbe.domain.time.dto.response;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;


public record TimeTableResponse(LinkedHashMap<LocalDateTime, Integer> timeTable) {

    public static TimeTableResponse from(LinkedHashMap<LocalDateTime, Integer> timeTable) {
        return new TimeTableResponse(timeTable);
    }

}
