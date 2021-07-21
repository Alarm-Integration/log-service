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
@Entity(name = "alarm_results")
public class AlarmResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private AlarmRequestEntity alarmRequestEntity;

    @Column(nullable = false, name = "app_name")
    private String appName;

    @Column(nullable = false, name = "log_message")
    private String logMessage;

    @Column(nullable = false, name = "is_success")
    private boolean isSuccess;

    @Column(nullable = false, name = "address")
    private String address;

    @Builder
    public AlarmResultEntity(Long id, String appName, String logMessage, boolean isSuccess, String address, AlarmRequestEntity alarmRequestEntity) {
        this.id = id;
        this.appName = appName;
        this.logMessage = logMessage;
        this.isSuccess = isSuccess;
        this.address = address;
        this.alarmRequestEntity = alarmRequestEntity;
    }

}
