package com.gabia.logservice.dto;


import com.gabia.logservice.domain.log.LogEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class AlarmResultResponseTest {
    @Test
    public void 객체_Getter_메소드_테스트() {
        // given
        Long id = 1L;
        Long userId = 1L;
        String traceId = "test_trace_id";
        String appName = "test_app_name";
        String resultMsg = "test_result_msg";
        LocalDateTime createdAt = LocalDateTime.now();
        LogEntity logEntity = LogEntity.builder()
                                            .id(id)
                                            .userId(userId)
                                            .traceId(traceId)
                                            .resultMsg(resultMsg)
                                            .appName(appName)
                                            .createdAt(createdAt)
                                            .build();

        // when
        AlarmResultResponse response = new AlarmResultResponse(logEntity);

        // then
        assertThat(response.getAppName()).isEqualTo(appName);
        assertThat(response.getResultMsg()).isEqualTo(resultMsg);
        assertThat(response.getCreatedAt()).isEqualTo(createdAt);

    }
}
