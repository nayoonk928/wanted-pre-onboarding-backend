package com.wanted.jobportal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PostTest {

  @Test
  void testJobPostingFields() {
    //given
    Long id = 1L;
    String position = "백엔드 주니어 개발자";
    int reward = 150000;
    String description = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
    String skill = "Python";

    //when
    Post post = Post.builder()
        .id(id)
        .position(position)
        .reward(reward)
        .description(description)
        .skill(skill)
        .build();

    //then
    assertEquals(id, post.getId());
    assertEquals(position, post.getPosition());
    assertEquals(reward, post.getReward());
    assertEquals(description, post.getDescription());
    assertEquals(skill, post.getSkill());
  }

  @Test
  void testJobPostingGettersAndSetters() {
    //given
    Post post = new Post();

    //when
    post.setId(1L);
    post.setPosition("백엔드 주니어 개발자");
    post.setReward(150000);
    post.setDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
    post.setSkill("Python");

    //then
    assertEquals(1L, post.getId());
    assertEquals("백엔드 주니어 개발자", post.getPosition());
    assertEquals(150000, post.getReward());
    assertEquals("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", post.getDescription());
    assertEquals("Python", post.getSkill());
  }

  @Test
  void testJobPostingCompanyRelationship() {
    //given
    Company company = new Company();
    Post post = new Post();

    //when
    post.setCompany(company);

    //then
    assertEquals(company, post.getCompany());
  }

}