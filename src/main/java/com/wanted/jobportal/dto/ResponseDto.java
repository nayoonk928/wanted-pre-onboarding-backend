package com.wanted.jobportal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ResponseDto {

  @JsonProperty("message")
  private String message;

  @JsonProperty("object")
  private Object object;

  public ResponseDto(String message, Object object) {
    this.message = message;
    this.object = object;
  }

}
