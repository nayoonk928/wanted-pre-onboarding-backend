package com.wanted.jobportal.service;

import com.wanted.jobportal.dto.JobPostingDto;
import com.wanted.jobportal.dto.JobPostingUpdateDto;
import org.springframework.http.ResponseEntity;

public interface JobPostingService {

  ResponseEntity<String> createJobPosting(JobPostingDto jobPostingDto);
  ResponseEntity<String> updateJobPosting(JobPostingUpdateDto jobPostingUpdateDto);
  ResponseEntity<String> deleteJobPosting(Long id);

}
