package com.gabia.logservice.controller;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import com.gabia.logservice.dto.AlarmResultResponse;
import com.gabia.logservice.dto.TraceIdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogRepository logRepository;
    private Long userId = 1L;
    private String traceId = "test_trace_id";
    private String appName = "test_app_name";
    private String resultMsg = "test_result_msg";
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();

        LogEntity defaultLogEntity = LogEntity.builder()
                .userId(userId)
                .traceId(traceId)
                .appName(appName)
                .resultMsg(resultMsg)
                .createdAt(createdAt)
                .build();

        logRepository.save(defaultLogEntity);
    }

    @Test
    void test_getAlarmTraceIds_empty_success() throws Exception {
        // given
        Long differentUserId = 2L;
        String expectedSuccessMessage = "알림 발송 결과 조회용 아이디 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header("user-id", differentUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result").isEmpty());
    }

    @Test
    void test_getAlarmTraceIds_success() throws Exception {
        // given
        String expectedSuccessMessage = "알림 발송 결과 조회용 아이디 결과 조회 완료";
        List<TraceIdResponse> expectedAlarmResultIdResponseList = new ArrayList<>();
        expectedAlarmResultIdResponseList.add(new TraceIdResponse(traceId, createdAt));

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header("user-id", userId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result.length()").value(expectedAlarmResultIdResponseList.size()))
                .andExpect(jsonPath("$..result[0].traceId").value(traceId))
                .andExpect(jsonPath("$..result[0].createdAt").value(createdAt.toString()));
    }

    @Test
    void test_getAlarmTraceIds_without_user_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "user-id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type Long is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
//                .header("user_id", userId)
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.requiredHeaderName").value(missingHeaderName));
    }

    @Test
    void test_getAlarmTraceIds_with_type_mismatch_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MethodArgumentTypeMismatchException";
        String typeMismatchedHeaderName = "user-id";
        String typeMismatchedUserId = "typeMismatchedUserId";
        String expectedErrorMessage = String.format("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"", typeMismatchedUserId);

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header(typeMismatchedHeaderName, typeMismatchedUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.typeMismatchedHeaderName").value(typeMismatchedHeaderName));
    }


    @Test
    void test_getAlarmResults_empty_success() throws Exception {
        // given
        Long differentUserId = 2L;
        String expectedSuccessMessage = "알림 발송 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-results?trace-id=%s", traceId))
                .header("user-id", differentUserId)
                .header("alarm-result-id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result").isEmpty());
    }

    @Test
    void test_getAlarmResults_success() throws Exception {
        // given
        String expectedSuccessMessage = "알림 발송 결과 조회 완료";
        List<AlarmResultResponse> expectedAlarmResultResponseList = new ArrayList<>();
        expectedAlarmResultResponseList.add(new AlarmResultResponse(LogEntity.builder()
                .userId(userId)
                .traceId(traceId)
                .appName(appName)
                .resultMsg(resultMsg)
                .createdAt(createdAt)
                .build()));

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-results?trace-id=%s", traceId))
                .header("user-id", userId)
                .header("alarm-result-id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result.length()").value(expectedAlarmResultResponseList.size()))
                .andExpect(jsonPath("$..result[0].appName").value(appName))
                .andExpect(jsonPath("$..result[0].resultMsg").value(resultMsg))
                .andExpect(jsonPath("$..result[0].createdAt").value(createdAt.toString()));
    }

    @Test
    void test_getAlarmResults_without_user_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "user-id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type Long is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-results?trace-id=%s", traceId))
//                .header("user_id", userId)
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.requiredHeaderName").value(missingHeaderName));
    }

    @Test
    void test_getAlarmResults_without_trace_id_query_param_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingServletRequestParameterException";
        String missingServletRequestParameter = "trace-id";
        String expectedErrorMessage = String.format("Required request parameter '%s' for method parameter type String is not present", missingServletRequestParameter);

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-results")
                .header("user-id", userId)
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.missingServletRequestParameter").value(missingServletRequestParameter));
    }

    @Test
    void test_getAlarmResults_with_type_mismatch_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MethodArgumentTypeMismatchException";
        String typeMismatchedHeaderName = "user-id";
        String typeMismatchedUserId = "typeMismatchedUserId";
        String expectedErrorMessage = String.format("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"", typeMismatchedUserId);

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-results?trace-id=%s", traceId))
                .header(typeMismatchedHeaderName, typeMismatchedUserId)
                .header("alarm-result-id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.typeMismatchedHeaderName").value(typeMismatchedHeaderName));
    }
}


