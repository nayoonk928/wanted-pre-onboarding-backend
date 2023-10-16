package com.wanted.jobportal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {

  private ErrorCode errorCode;
  private String message;
  private HttpStatus httpStatus;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = errorCode.getMessage();
    this.httpStatus = errorCode.getHttpStatus();
  }

}
