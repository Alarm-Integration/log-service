package com.gabia.logservice.domain.log;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "logs")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "trace_id")
    private String traceId;

    @Column(nullable = false, name = "app_name")
    private String appName;

    @Column(nullable = false, name = "result_msg")
    private String resultMsg;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public LogEntity(Long id, Long userId, String traceId, String appName, String resultMsg, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.traceId = traceId;
        this.appName = appName;
        this.resultMsg = resultMsg;
        this.createdAt = createdAt;
    }

}
