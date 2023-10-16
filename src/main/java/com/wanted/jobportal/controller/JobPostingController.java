package com.wanted.jobportal.controller;

import com.wanted.jobportal.dto.JobPostingDto;
import com.wanted.jobportal.dto.JobPostingUpdateDto;
import com.wanted.jobportal.service.JobPostingService;
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
public class JobPostingController {

  private final JobPostingService jobPostingService;

  @PostMapping("/post")
  public ResponseEntity<String> createJobPosting(@RequestBody JobPostingDto dto) {
    return jobPostingService.createJobPosting(dto);
  }

  @PatchMapping("/post")
  public ResponseEntity<String> modifyJobPosting(@RequestBody JobPostingUpdateDto dto) {
    return jobPostingService.updateJobPosting(dto);
  }

  @DeleteMapping("/post/{id}")
  public ResponseEntity<String> deleteJobPosting(@PathVariable Long id) {
    return jobPostingService.deleteJobPosting(id);
  }

}