package com.wanted.jobportal.repository;

import com.wanted.jobportal.domain.Application;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  boolean existsByPostAndUser(Post post, User user);

}
