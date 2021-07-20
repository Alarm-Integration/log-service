package com.gabia.logservice.dto;


import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class AlarmResultResponseTest {
    @Test
    public void test_get_method() {
        // given
        String address = "01012341234";
        String appName = "test_app_name";
        boolean isSuccess = true;
        String logMessage = "test_log_message";
        AlarmResultEntity alarmResultEntity = AlarmResultEntity.builder()
                .appName(appName)
                .isSuccess(true)
                .logMessage(logMessage)
                .address(address)
                .build();

        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();
        AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity.builder()
                .requestId(requestId)
                .userId(userId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();

        // when
        AlarmResultResponse response = new AlarmResultResponse(alarmResultEntity, alarmRequestEntity);

        // then
        assertThat(response.getAppName()).isEqualTo(appName);
        assertThat(response.isSuccess()).isEqualTo(isSuccess);
        assertThat(response.getLogMessage()).isEqualTo(logMessage);
        assertThat(response.getAddress()).isEqualTo(address);
        assertThat(response.getCreatedAt()).isEqualTo(createdAt);
    }
}
