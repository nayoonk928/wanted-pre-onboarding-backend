package com.wanted.jobportal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.domain.User;
import com.wanted.jobportal.dto.ApplicationDto;
import com.wanted.jobportal.dto.ResponseDto;
import com.wanted.jobportal.repository.ApplicationRepository;
import com.wanted.jobportal.repository.PostRepository;
import com.wanted.jobportal.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ApplicationServiceImplTest {

  @InjectMocks
  private ApplicationServiceImpl applicationService;

  @Mock
  private ApplicationRepository applicationRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PostRepository postRepository;

  @BeforeEach
  void setUp() {
    applicationRepository = mock(ApplicationRepository.class);
    userRepository = mock(UserRepository.class);
    postRepository = mock(PostRepository.class);
    applicationService = new ApplicationServiceImpl(applicationRepository, userRepository, postRepository);
  }

  @Test
  @DisplayName("지원하기 - 성공")
  void apply_Success() {
    //given
    User user = User.builder()
        .id(1L)
        .name("홍길동")
        .build();

    userRepository.save(user);

    Post post = Post.builder()
        .id(1L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .build();

    postRepository.save(post);

    ApplicationDto applicationDto = new ApplicationDto();
    applicationDto.setUserId(1L);
    applicationDto.setPostId(2L);

    //when
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    Mockito.when(postRepository.findById(2L)).thenReturn(Optional.of(post));
    Mockito.when(applicationRepository.existsByPostAndUser(post, user)).thenReturn(false);

    //then
    ResponseDto responseDto = applicationService.apply(applicationDto);

    assertNotNull(responseDto);
    assertEquals("지원이 완료되었습니다.", responseDto.getMessage());
  }

  @Test
  @DisplayName("지원하기 - 실패")
  void apply_Failure() {
    //given
    User user = User.builder()
        .id(1L)
        .name("홍길동")
        .build();

    userRepository.save(user);

    Post post = Post.builder()
        .id(1L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .build();

    postRepository.save(post);

    ApplicationDto applicationDto = new ApplicationDto();
    applicationDto.setUserId(1L);
    applicationDto.setPostId(2L);

    //when
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    Mockito.when(postRepository.findById(2L)).thenReturn(Optional.of(post));
    Mockito.when(applicationRepository.existsByPostAndUser(post, user)).thenReturn(true);

    //then
    ResponseDto responseDto = applicationService.apply(applicationDto);

    assertNotNull(responseDto);
    assertEquals("이미 이 채용 공고에 지원하셨습니다.", responseDto.getMessage());
  }

}