package com.wanted.jobportal.dto;

import com.wanted.jobportal.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {

  private Long postId;
  private String position;
  private int reward;
  private String description;
  private String skill;

  public static PostDto of(Post post) {
    return PostDto.builder()
        .postId(post.getId())
        .position(post.getPosition())
        .reward(post.getReward())
        .description(post.getDescription())
        .skill(post.getSkill())
        .build();
  }

}
