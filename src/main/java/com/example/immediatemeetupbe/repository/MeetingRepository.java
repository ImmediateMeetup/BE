package com.example.immediatemeetupbe.repository;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting getById(Long id);
    boolean existsByMemberId(Long memberId);

    Meeting getMeetingByMemberId(Long memberId);
}
