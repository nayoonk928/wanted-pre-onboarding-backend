package com.wanted.jobportal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateDto {

  private Long postId;
  private String position;
  private int reward;
  private String description;
  private String skill;

}
