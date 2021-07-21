package com.gabia.logservice.dto;

import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AlarmResponseTest {
    @Test
    public void test_get_method() {
        // given
        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();

        String appName = "test_app_name";
        boolean isSuccess = true;
        String logMessage = "test_log_message";
        String address = "01012341234";

        AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity
                .builder()
                .userId(userId)
                .requestId(requestId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();

        int expectedAlarmResultEntitySize = 3;
        List<AlarmResultResponse> alarmResultResponseList = new ArrayList<>();


        for (int i = 0; i < expectedAlarmResultEntitySize; i++) {
            AlarmResultEntity alarmResultEntity = AlarmResultEntity.builder()
                    .appName(appName)
                    .isSuccess(isSuccess)
                    .logMessage(logMessage + i)
                    .address(address)
                    .build();
            alarmResultResponseList.add(new AlarmResultResponse(alarmResultEntity, alarmRequestEntity));
        }

        // when
        AlarmResponse response = new AlarmResponse(alarmRequestEntity, alarmResultResponseList);

        // then
        assertThat(response.getTitle()).isEqualTo(title);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getResultCount()).isEqualTo(expectedAlarmResultEntitySize);
        assertThat(response.getAlarmResultResponseList()).containsAll(alarmResultResponseList);
    }
}
