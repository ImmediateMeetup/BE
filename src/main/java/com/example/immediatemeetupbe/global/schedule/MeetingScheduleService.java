package com.example.immediatemeetupbe.global.schedule;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import com.example.immediatemeetupbe.domain.meeting.entity.Status;
import com.example.immediatemeetupbe.domain.meeting.repository.MeetingRepository;
import com.example.immediatemeetupbe.domain.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingScheduleService {

    private final MeetingRepository meetingRepository;

    @Transactional
    @Scheduled(cron = "0 59 23 * * ?")
    public void disableMeetingsAtSpecificDate() {
        LocalDate specificDate = LocalDate.now();
        List<Meeting> meetingsToDisable = meetingRepository.findByLastDay(specificDate.toString());

        meetingsToDisable.forEach(meeting -> {
            meeting.controlStatus(Status.DEACTIVATED);
        });
    }
}
