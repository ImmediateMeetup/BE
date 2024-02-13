package com.example.immediatemeetupbe.domain.meeting.entity;

import com.example.immediatemeetupbe.domain.comment.entity.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "first_day")
    private String firstDay;

    @Column(name = "last_day")
    private String lastDay;

    @Column(name = "place")
    private String place;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "status")
    private boolean status;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "meeting", orphanRemoval = true)
    private List<Comment> commentMeetingList;


    public void update(String title, String content, String firstDay, String lastDay) {
        this.title = title;
        this.content = content;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
    }

}
