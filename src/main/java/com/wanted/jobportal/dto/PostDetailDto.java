package com.wanted.jobportal.dto;

import com.wanted.jobportal.domain.Post;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailDto {

  private Long postId;
  private String companyName;
  private String country;
  private String region;
  private String position;
  private int reward;
  private String description;
  private String skill;
  private List<Long> otherPostIds;

  public static PostDetailDto from(Post post, List<Post> allPosts) {
    return PostDetailDto.builder()
        .postId(post.getId())
        .companyName(post.getCompany().getName())
        .country(post.getCompany().getCountry())
        .region(post.getCompany().getRegion())
        .position(post.getPosition())
        .reward(post.getReward())
        .description(post.getDescription())
        .skill(post.getSkill())
        .otherPostIds(getOtherPostIds(allPosts, post.getId()))
        .build();
  }

  private static List<Long> getOtherPostIds(List<Post> allPosts, Long currentPostId) {
    if (allPosts == null) {
      return Collections.emptyList();
    }

    return allPosts.stream()
        .map(Post::getId)
        .filter(id -> !id.equals(currentPostId))
        .collect(Collectors.toList());
  }

}
