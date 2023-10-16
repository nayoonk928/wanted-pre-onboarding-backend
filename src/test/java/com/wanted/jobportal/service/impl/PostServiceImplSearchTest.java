package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_JOB_POSTING_ID;
import static com.wanted.jobportal.exception.ErrorCode.JOB_POSTING_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.PostRepository;
import com.wanted.jobportal.service.PostService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostServiceImplSearchTest {

  private PostService postService;

  private CompanyRepository companyRepository;

  private PostRepository postRepository;

  private Company company;

  @BeforeEach
  void setUp() {
    companyRepository = mock(CompanyRepository.class);
    postRepository = mock(PostRepository.class);
    postService = new PostServiceImpl(companyRepository, postRepository);

    company = Company.builder()
        .name("원티드랩")
        .country("한국")
        .region("서울")
        .build();

    companyRepository.save(company);

    Post post1 = Post.builder()
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .company(company)
        .build();

    Post post2 = Post.builder()
        .position("Django 백엔드 개발자")
        .reward(1000000)
        .description("네이버에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Django")
        .company(company)
        .build();

    postRepository.save(post1);
    postRepository.save(post2);
  }

  @Test
  @DisplayName("모든 채용공고 보기 - 성공")
  void getAllPosts_Success() {
    //given
    List<Post> savedPosts = postRepository.findAll();
    int expectedSize = savedPosts.size();

    //when
    List<PostListDto> postListDtos = postService.getAllPosts();

    //then
    assertEquals(expectedSize, postListDtos.size());
  }

}