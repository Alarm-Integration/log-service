package com.gabia.logservice.exception;

import com.gabia.logservice.dto.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> missingRequestHeaderExceptionHandler(MissingRequestHeaderException e){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("required_header_name", e.getHeaderName());
        errorBody.put("error_message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MissingRequestHeaderException", errorBody));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("type_mismatched_header_name", e.getName());
        errorBody.put("error_message", e.getLocalizedMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MethodArgumentTypeMismatch", errorBody));
    }

}