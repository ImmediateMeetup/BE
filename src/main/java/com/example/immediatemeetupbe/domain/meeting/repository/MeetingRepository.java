package com.example.immediatemeetupbe.domain.meeting.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByLastDay(String LastDay);
}
