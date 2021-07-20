package com.gabia.logservice.exception;

import com.gabia.logservice.dto.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<APIResponse> entityNotFoundExceptionHandler(EntityNotFoundException e){
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MissingRequestHeaderException", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> missingRequestHeaderExceptionHandler(MissingRequestHeaderException e){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("requiredHeaderName", e.getHeaderName());
        errorBody.put("errorMessage", e.getMessage());

        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MissingRequestHeaderException", errorBody));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("typeMismatchedHeaderName", e.getName());
        errorBody.put("errorMessage", e.getLocalizedMessage());

        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MethodArgumentTypeMismatchException", errorBody));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("missingServletRequestParameter", e.getParameterName());
        errorBody.put("errorMessage", e.getLocalizedMessage());

        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.withMessage("MissingServletRequestParameterException", errorBody));
    }

}