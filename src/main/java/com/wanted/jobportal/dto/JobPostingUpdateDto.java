package com.wanted.jobportal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobPostingUpdateDto {

  private Long jobPostingId;
  private String position;
  private int reward;
  private String description;
  private String skill;

}
