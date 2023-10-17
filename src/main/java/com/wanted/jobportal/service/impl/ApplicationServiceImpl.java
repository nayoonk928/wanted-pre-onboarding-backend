package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.POST_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.USER_NOT_FOUND;

import com.wanted.jobportal.domain.Application;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.domain.User;
import com.wanted.jobportal.dto.ApplicationDto;
import com.wanted.jobportal.dto.ResponseDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.ApplicationRepository;
import com.wanted.jobportal.repository.PostRepository;
import com.wanted.jobportal.repository.UserRepository;
import com.wanted.jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  @Override
  @Transactional
  public ResponseDto apply(ApplicationDto applicationDto) {
    User user = userRepository.findById(applicationDto.getUserId())
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    Post post = postRepository.findById(applicationDto.getPostId())
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    boolean hasAlreadyApplied = applicationRepository.existsByPostAndUser(post, user);

    if (hasAlreadyApplied) {
      return new ResponseDto("이미 이 채용 공고에 지원하셨습니다.", null);
    }

    Application apply = Application.builder()
        .post(post)
        .user(user)
        .build();

    applicationRepository.save(apply);
    return new ResponseDto("지원이 완료되었습니다.", applicationDto);
  }

}
