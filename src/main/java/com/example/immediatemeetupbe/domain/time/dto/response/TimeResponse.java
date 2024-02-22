package com.example.immediatemeetupbe.domain.time.dto.response;

import lombok.Getter;

@Getter
public class TimeResponse {

    private final String[] timeList;

    public TimeResponse(String[] timeList) {
        this.timeList = timeList;
    }

    public static TimeResponse from(String[] timeList) {
        return new TimeResponse(timeList);
    }
}


