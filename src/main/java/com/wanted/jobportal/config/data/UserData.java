package com.wanted.jobportal.config.data;

import com.wanted.jobportal.domain.User;
import com.wanted.jobportal.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserData {

  private final UserRepository userRepository;

  @PostConstruct
  public void init() {
    User user1 = User.builder()
        .name("홍길동")
        .build();

    User user2 = User.builder()
        .name("김철수")
        .build();

    userRepository.saveAll(List.of(user1, user2));
  }

}
