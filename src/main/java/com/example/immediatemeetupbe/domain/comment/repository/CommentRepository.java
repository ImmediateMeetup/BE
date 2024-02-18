package com.example.immediatemeetupbe.domain.comment.repository;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
