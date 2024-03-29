package com.wanted.jobportal.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다.", BAD_REQUEST),
  INVALID_REQUEST("잘못된 요청입니다.", BAD_REQUEST),
  COMPANY_NOT_FOUND("해당 회사가 존재하지 않습니다.", BAD_REQUEST),
  POST_NOT_FOUND("해당 채용공고가 존재하지 않습니다.", BAD_REQUEST),
  INVALID_POST_ID("채용공고의 아이디가 올바르지 않습니다.",  BAD_REQUEST),
  USER_NOT_FOUND("존재하지 않는 사용자입니다.", BAD_REQUEST)
  ;

  private final String message;
  private final HttpStatus httpStatus;

}