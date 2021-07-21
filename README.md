## Log Service

> 알림 통합 서비스에서 알림을 발송한 사용자가 그 결과를 알고 싶을 때 사용하는 api server

### APIs

1. GET /log-service/alarmResults
    - 알림 발송 결과 조회 api
    - request header
        - "user-id" : "1"
        - "trace-id" : "989e7dc5-6150-4764-8a34-cd929631018e"
    - response
        1. 200 OK
             ```json
             {
               "message": "알림 발송 결과 조회 완료",
               "result": [
                   {
                       "app_name": "sms",
                       "result_msg": "문자 발송 성공",
                       "created_at": "2021-07-19T09:16:03"
                   },
                   {
                       "app_name": "slack",
                       "result_msg": "슬랙 발송 성공",
                       "created_at": "2021-07-19T09:16:03.456614"
                   },
                   {
                       "app_name": "email",
                       "result_msg": "메일 발송 성공",
                       "created_at": "2021-07-19T09:16:03.576514"
                   }
               ]
             }
             ```
        2. 400 BAD REQUEST - request header에 필수 key값이 빠졌을 때 (e.g. user-id이 빠졌을 때)
            ```json
             {
               "message": "MissingRequestHeaderException",
               "result": {
                  "error_message": "Required request header 'user-id' for method parameter type Long is not present",
                  "required_header_name": "user-id"
               }
             }
             ```
        3. 400 BAD REQUEST - request header에 값 타입이 틀렸을 때 (e.g. user-id=test)
            ```json
             {
                 "message": "MethodArgumentTypeMismatch",
                 "result": {
                    "error_message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"sdf\"",
                    "type_mismatched_header_name": "user-id"
                 }
             }
             ```
2. GET /log-service/alarmResultIds
    - 사용자의 알림 발송 조회 아이디 목록 조회 api
    - request header
        - "user-id" : "1"
    - response
        1. 200 OK
             ```json
             {
               "message": "알림 발송 결과 조회 완료",
               "result": [
                  {
                     "alarm_result_id": "2086bce1-3611-4d91-939f-e4cf525d1a8a",
                     "created_at": "2021-07-18T20:58:37.883275"
                  },
                  {
                     "alarm_result_id": "2bf101e9-3f7f-4a7b-8db1-4af86ec366b3",
                     "created_at": "2021-07-18T16:50:38.460447"
                  }
               ]
             }   
             ```
        2. 400 BAD REQUEST - request header에 필수 key값이 빠졌을 때 (e.g. user-id이 빠졌을 때)
            ```json
             {
               "message": "MissingRequestHeaderException",
               "result": {
                  "error_message": "Required request header 'user-id' for method parameter type Long is not present",
                  "required_header_name": "user-id"
               }
             }
             ```
        3. 400 BAD REQUEST - request header에 값 타입이 틀렸을 때 (e.g. user-id=test)
            ```json
             {
                 "message": "MethodArgumentTypeMismatch",
                 "result": {
                    "error_message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"sdf\"",
                    "type_mismatched_header_name": "user-id"
                 }
             }
             ```
           
## JPA QUERY
```java
package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.AlarmResultIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntity, Long> {

    // request header로 넘어온 user-id와 trace-id를 받아서 이를 동시에 충족하는 log테이블의 rows 반환
    // SELECT * FROM logs WHERE user_id = :userId AND trace_id = :traceId
    List<LogEntity> findByUserIdAndTraceId(Long userId, String traceId);

    // request header로 넘어온 user-id를 받아서 해당하는 중복되지않는 trace_id 리스트를 생성날짜와 함께 rows 반환
    @Query("SELECT new com.gabia.logservice.dto.AlarmResultIdResponse(l.traceId, MIN(l.createdAt)) FROM logs l WHERE l.userId = :userId GROUP BY l.traceId")
    List<AlarmResultIdResponse> findAllByUserId(@Param("userId") Long userId);

}

```