package com.wanted.jobportal.controller;

import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
public class PostController {

  private final PostService postService;

  @PostMapping("/post")
  public ResponseEntity<String> createJobPosting(@RequestBody PostAddDto dto) {
    return postService.createJobPosting(dto);
  }

  @PatchMapping("/post")
  public ResponseEntity<String> modifyJobPosting(@RequestBody PostUpdateDto dto) {
    return postService.updateJobPosting(dto);
  }

  @DeleteMapping("/post/{id}")
  public ResponseEntity<String> deleteJobPosting(@PathVariable Long id) {
    return postService.deleteJobPosting(id);
  }

}