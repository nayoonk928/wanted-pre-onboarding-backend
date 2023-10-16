package com.wanted.jobportal.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException e) {
    ErrorResponse response = ErrorResponse.builder()
        .message(e.getMessage())
        .build();
    log.error("{} is occurred.", e.getMessage());
    return new ResponseEntity<>(response, e.getHttpStatus());
  }

  public static ResponseEntity<?> getErrorResponseList(Errors errors) {
    List<ErrorResponse> errorResponseList = new ArrayList<>();

    errors.getAllErrors().forEach((error) -> {
      errorResponseList.add(ErrorResponse.of((FieldError) error));
      log.error("Field error occurred: {}", error.getDefaultMessage());
    });

    return new ResponseEntity<>(errorResponseList, HttpStatus.BAD_REQUEST);
  }

}
