package com.example.immediatemeetupbe.domain.notice.repository;


import com.example.immediatemeetupbe.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
