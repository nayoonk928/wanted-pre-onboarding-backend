package com.wanted.jobportal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JobPostingTest {

  @Test
  void testJobPostingFields() {
    //given
    Long id = 1L;
    String position = "백엔드 주니어 개발자";
    int reward = 150000;
    String description = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
    String skill = "Python";

    //when
    JobPosting jobPosting = JobPosting.builder()
        .id(id)
        .position(position)
        .reward(reward)
        .description(description)
        .skill(skill)
        .build();

    //then
    assertEquals(id, jobPosting.getId());
    assertEquals(position, jobPosting.getPosition());
    assertEquals(reward, jobPosting.getReward());
    assertEquals(description, jobPosting.getDescription());
    assertEquals(skill, jobPosting.getSkill());
  }

  @Test
  void testJobPostingGettersAndSetters() {
    //given
    JobPosting jobPosting = new JobPosting();

    //when
    jobPosting.setId(1L);
    jobPosting.setPosition("백엔드 주니어 개발자");
    jobPosting.setReward(150000);
    jobPosting.setDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
    jobPosting.setSkill("Python");

    //then
    assertEquals(1L, jobPosting.getId());
    assertEquals("백엔드 주니어 개발자", jobPosting.getPosition());
    assertEquals(150000, jobPosting.getReward());
    assertEquals("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", jobPosting.getDescription());
    assertEquals("Python", jobPosting.getSkill());
  }

  @Test
  void testJobPostingCompanyRelationship() {
    //given
    Company company = new Company();
    JobPosting jobPosting = new JobPosting();

    //when
    jobPosting.setCompany(company);

    //then
    assertEquals(company, jobPosting.getCompany());
  }

}