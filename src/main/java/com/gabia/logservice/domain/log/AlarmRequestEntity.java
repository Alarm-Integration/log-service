package com.gabia.logservice.domain.log;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "alarm_requests")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlarmRequestEntity {

    @Id
    @Column(nullable = false, name = "request_id")
    @EqualsAndHashCode.Include
    private String requestId;

    @Column(nullable = false, name = "user_id")
    @EqualsAndHashCode.Include
    private Long userId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alarmRequestEntity")
    private List<AlarmResultEntity> alarmResultEntityList;

    @Builder
    public AlarmRequestEntity(String requestId, Long userId, String title, String content, LocalDateTime createdAt, List<AlarmResultEntity> alarmResultEntityList) {
        this.requestId = requestId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.alarmResultEntityList = alarmResultEntityList;
    }
}
