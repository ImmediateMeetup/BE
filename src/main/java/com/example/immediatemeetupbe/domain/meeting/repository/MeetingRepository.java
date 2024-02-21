package com.example.immediatemeetupbe.domain.meeting.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
