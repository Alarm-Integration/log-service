package com.gabia.logservice.domain.log;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "alarm_logs")
public class AlarmLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "trace_id")
    private String traceId;

    @Column(nullable = false, name = "app_name")
    private String appName;

    @Column(nullable = false, name = "log_message")
    private String logMessage;

    @Column(nullable = false, name = "is_success")
    private boolean isSuccess;

    @Column(nullable = false, name = "receiver")
    private String receiver;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;



    @Builder
    public AlarmLogEntity(Long id, Long userId, String traceId, String appName, String logMessage, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.traceId = traceId;
        this.appName = appName;
        this.logMessage = logMessage;
        this.createdAt = createdAt;
    }

}
