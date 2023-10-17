package com.wanted.jobportal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.dto.PostDetailDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.PostRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class PostServiceImplSearchTest {

  @InjectMocks
  private PostServiceImpl postService;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PostRepository postRepository;

  private Company company;
  private Post post1;
  private Post post2;

  @BeforeEach
  void setUp() {
    companyRepository = mock(CompanyRepository.class);
    postRepository = mock(PostRepository.class);
    postService = new PostServiceImpl(companyRepository, postRepository);

    company = Company.builder()
        .id(1L)
        .name("원티드랩")
        .country("한국")
        .region("서울")
        .build();

    companyRepository.save(company);

    post1 = Post.builder()
        .id(1L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .company(company)
        .build();

    post2 = Post.builder()
        .id(2L)
        .position("Django 백엔드 개발자")
        .reward(1000000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
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

  @Test
  @DisplayName("채용공고 겁색 - 성공")
  void searchPosts_Success() {
    //given
    String keyword = "Python";

    //when
    when(postRepository.searchPosts(keyword)).thenReturn(Arrays.asList(post1));
    List<PostListDto> postListDtos = postService.searchPosts(keyword);

    //then
    assertEquals(1, postListDtos.size());
    assertEquals("Python", postListDtos.get(0).getSkill());
  }

  @Test
  @DisplayName("채용공고 겁색(회사) - 성공")
  void searchPosts_CompanyName() {
    //given
    String keyword = "원티드랩";

    //when
    when(postRepository.searchPosts(keyword)).thenReturn(Arrays.asList(post1, post2));
    List<PostListDto> postListDtos = postService.searchPosts(keyword);

    //then
    assertEquals(2, postListDtos.size());
    assertEquals("원티드랩", postListDtos.get(0).getCompanyName());
  }

  @Test
  @DisplayName("채용공고 상세정보 - 성공")
  void getPostDetail_Success() {
    //given
    Long postId = 1L;
    when(postRepository.findById(postId)).thenReturn(Optional.of(post1));
    List<Post> allPosts = Arrays.asList(post1, post2);

    //when
    Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post1));

    // Mock PostDetailDto.from(post)
    PostDetailDto postDetailDto = PostDetailDto.from(post1, allPosts);

    //then
    assertEquals("백엔드 주니어 개발자", postDetailDto.getPosition());
    assertEquals(1500000, postDetailDto.getReward());
    assertEquals(1, postDetailDto.getOtherPostIds().size());
  }

}