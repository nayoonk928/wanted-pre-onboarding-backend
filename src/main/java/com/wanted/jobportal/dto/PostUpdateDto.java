package com.wanted.jobportal.dto;

import com.wanted.jobportal.domain.Post;
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

  public static PostUpdateDto of(Post post) {
    return PostUpdateDto.builder()
        .postId(post.getId())
        .position(post.getPosition())
        .reward(post.getReward())
        .description(post.getDescription())
        .skill(post.getSkill())
        .build();
  }

}
