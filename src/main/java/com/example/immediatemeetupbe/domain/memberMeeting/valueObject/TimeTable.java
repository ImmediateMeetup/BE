package com.example.immediatemeetupbe.domain.memberMeeting.valueObject;

import com.example.immediatemeetupbe.domain.memberMeeting.entity.MeetingMember;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.Getter;

@Getter
public class TimeTable {

    private final LinkedHashMap<LocalDateTime, Integer> timeTable;

    public TimeTable(MeetingTime meetingTime) {
        timeTable = new LinkedHashMap<>();
        LocalDateTime currentDateTime = meetingTime.getFirstDateTime();
        LocalDateTime lastDateTime = meetingTime.getLastDateTime();
        while (currentDateTime.isBefore(lastDateTime) || currentDateTime.isEqual(
            lastDateTime)) {
            timeTable.put(currentDateTime, 0);
            currentDateTime = currentDateTime.plusMinutes(30);
        }
    }

    public void calculateSchedule(List<MeetingMember> meetingMemberList) {
        meetingMemberList.forEach(meetingMember -> {
            Arrays.stream(meetingMember.getTimeZone().split("/"))
                .map(LocalDateTime::parse)
                .forEach(
                    timezone -> {
                        timeTable.put(timezone, timeTable.get(timezone) + 1);
                    });
        });
    }
}
