package com.example.immediatemeetupbe.domain.participant.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
public class MeetingTime {

    private LocalTime firstTime;
    private LocalTime lastTime;

    private LocalDateTime firstDateTime;
    private LocalDateTime lastDateTime;

    public void setMeetingTime(String timeZone, String firstDay, String lastDay) {
        String[] timeArray = timeZone.split("~");
        this.firstTime = LocalTime.parse(timeArray[0]);
        this.lastTime = LocalTime.parse(timeArray[1]);
        this.firstDateTime = LocalDateTime.of(LocalDate.parse(firstDay), firstTime);
        this.lastDateTime = LocalDateTime.of(LocalDate.parse(lastDay), lastTime);
    }
}
