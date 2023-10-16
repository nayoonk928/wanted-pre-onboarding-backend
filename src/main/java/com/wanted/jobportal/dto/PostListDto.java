package com.wanted.jobportal.dto;

import com.wanted.jobportal.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListDto {

  private Long postId;
  private String companyName;
  private String country;
  private String region;
  private String position;
  private int reward;
  private String skill;

  public static PostListDto search(Post post) {
    PostListDto.PostListDtoBuilder builder = PostListDto.builder()
        .postId(post.getId())
        .companyName(post.getCompany().getName())
        .country(post.getCompany().getCountry())
        .region(post.getCompany().getRegion())
        .position(post.getPosition())
        .reward(post.getReward())
        .skill(post.getSkill());

    return builder.build();
  }

}
