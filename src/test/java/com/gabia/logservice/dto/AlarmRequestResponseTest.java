package com.gabia.logservice.dto;

import com.gabia.logservice.domain.log.AlarmRequestEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AlarmRequestResponseTest {
    @Test
    public void test_get_method() {
        // given
        String requestId = "test_request_id";
        Long userId = 1L;
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();

        AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity
                .builder()
                .userId(userId)
                .requestId(requestId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();

        // when
        AlarmRequestResponse response = AlarmRequestResponse
                .builder()
                .alarmRequestEntity(alarmRequestEntity)
                .build();

        // then
        assertThat(response.getRequestId()).isEqualTo(requestId);
        assertThat(response.getTitle()).isEqualTo(title);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getCreatedAt()).isEqualTo(createdAt);
    }
}
