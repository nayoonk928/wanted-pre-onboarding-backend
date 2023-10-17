package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_POST_ID;
import static com.wanted.jobportal.exception.ErrorCode.POST_NOT_FOUND;
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
import com.wanted.jobportal.dto.ResponseDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class PostServiceImplTest {

  @InjectMocks
  private PostServiceImpl postService;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PostRepository postRepository;


  @BeforeEach
  void setUp() {
    companyRepository = mock(CompanyRepository.class);
    postRepository = mock(PostRepository.class);
    postService = new PostServiceImpl(companyRepository, postRepository);
  }

  @Test
  @DisplayName("채용공고 등록 - 성공")
  void createPost_Success() {
    //given
    Company company = Company.builder()
        .name("원티드랩")
        .country("한국")
        .region("서울")
        .build();

    PostAddDto postAddDto = PostAddDto.builder()
        .companyId(1L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .build();

    //when
    when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
    ResponseDto response = postService.createPost(postAddDto);

    //then
    verify(companyRepository, times(1)).findById(1L);
    verify(postRepository, times(1)).save(any());
    assertEquals(response.getMessage(), "채용공고가 등록되었습니다.");
  }

  @Test
  @DisplayName("채용공고 등록 - 실패")
  void createPost_CompanyNotFound() {
    //given
    PostAddDto postAddDto = PostAddDto.builder()
        .companyId(2L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .build();

    //when
    when(companyRepository.findById(2L)).thenReturn(java.util.Optional.empty());

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> postService.createPost(postAddDto));
    assertEquals(COMPANY_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 수정 - 성공")
  void updatePost_Success() {
    //given
    PostUpdateDto updateDto = PostUpdateDto.builder()
        .postId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    Post existingPost = Post.builder()
        .id(1L)
        .position("기존 포지션")
        .reward(1000000)
        .description("기존 설명")
        .skill("Python")
        .build();

    //when
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPost));
    ResponseDto response = postService.updatePost(updateDto);

    //then
    verify(postRepository, times(1)).findById(1L);
    verify(postRepository, times(1)).save(any());
    assertEquals("채용공고가 수정되었습니다.", response.getMessage());
    assertEquals("업데이트된 포지션", existingPost.getPosition());
    assertEquals(2000000, existingPost.getReward());
    assertEquals("업데이트된 설명", existingPost.getDescription());
    assertEquals("Java", existingPost.getSkill());
  }

  @Test
  @DisplayName("채용공고 수정 - 실패")
  void updatePost_PostNotFound() {
    //given
    PostUpdateDto updateDto = PostUpdateDto.builder()
        .postId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    //when
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.empty());

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> postService.updatePost(updateDto));
    assertEquals(POST_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 수정 - 실패")
  void updatePost_FailWithModifiedPostId() {
    //given
    PostUpdateDto updateDto = PostUpdateDto.builder()
        .postId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    Post existingPost = Post.builder()
        .id(2L)
        .position("기존 포지션")
        .reward(1000000)
        .description("기존 설명")
        .skill("Python")
        .build();

    //when
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPost));

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> postService.updatePost(updateDto));
    assertEquals(INVALID_POST_ID, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 삭제 - 성공")
  void deletePost_Success() {
    //given
    Long jobPostingId = 1L;

    //when
    when(postRepository.existsById(jobPostingId)).thenReturn(true);
    String response = postService.deletePost(jobPostingId);

    //then
    verify(postRepository, times(1)).existsById(jobPostingId);
    verify(postRepository, times(1)).deleteById(jobPostingId);
    assertEquals("채용공고가 삭제되었습니다.", response);
  }

  @Test
  @DisplayName("채용공고 삭제 - 실패")
  void deletePost_PostNotFound() {
    //given
    Long jobPostingId = 1L;

    //when
    when(postRepository.existsById(jobPostingId)).thenReturn(false);

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> postService.deletePost(jobPostingId));
    assertEquals(POST_NOT_FOUND, exception.getErrorCode());
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