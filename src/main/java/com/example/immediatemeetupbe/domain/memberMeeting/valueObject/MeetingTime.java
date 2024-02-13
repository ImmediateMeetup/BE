package com.example.immediatemeetupbe.domain.memberMeeting.valueObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Hashtable;
import lombok.Getter;


@Getter
public class MeetingTime {

    private final LocalDateTime firstDateTime;
    private final LocalDateTime lastDateTime;

    public MeetingTime(String timeZone, String firstDay, String lastDay) {
        String[] timeArray = timeZone.split("~");
        LocalTime firstTime = LocalTime.parse(timeArray[0]);
        LocalTime lastTime = LocalTime.parse(timeArray[1]);
        this.firstDateTime = LocalDateTime.of(LocalDate.parse(firstDay), firstTime);
        this.lastDateTime = LocalDateTime.of(LocalDate.parse(lastDay), lastTime);
    }

}
