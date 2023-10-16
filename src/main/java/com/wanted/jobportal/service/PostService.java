package com.wanted.jobportal.service;

import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import org.springframework.http.ResponseEntity;

public interface PostService {

  ResponseEntity<String> createJobPosting(PostAddDto postAddDto);
  ResponseEntity<String> updateJobPosting(PostUpdateDto postUpdateDto);
  ResponseEntity<String> deleteJobPosting(Long id);

}
