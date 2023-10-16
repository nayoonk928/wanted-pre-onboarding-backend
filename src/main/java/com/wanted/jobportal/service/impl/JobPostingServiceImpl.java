package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_JOB_POSTING_ID;
import static com.wanted.jobportal.exception.ErrorCode.JOB_POSTING_NOT_FOUND;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.JobPosting;
import com.wanted.jobportal.dto.JobPostingDto;
import com.wanted.jobportal.dto.JobPostingUpdateDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.JobPostingRepository;
import com.wanted.jobportal.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingServiceImpl implements JobPostingService {

  private final CompanyRepository companyRepository;
  private final JobPostingRepository jobPostingRepository;

  @Override
  public ResponseEntity<String> createJobPosting(JobPostingDto jobPostingDto) {
    Company company = companyRepository.findById(jobPostingDto.getCompanyId())
        .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

    JobPosting jobPosting = JobPosting.builder()
        .company(company)
        .position(jobPostingDto.getPosition())
        .reward(jobPostingDto.getReward())
        .description(jobPostingDto.getDescription())
        .skill(jobPostingDto.getSkill())
        .build();

    jobPostingRepository.save(jobPosting);
    return ResponseEntity.ok().body("채용공고가 등록되었습니다.");
  }

  @Override
  public ResponseEntity<String> updateJobPosting(JobPostingUpdateDto jobPostingUpdateDto) {
    JobPosting jobPosting = jobPostingRepository.findById(jobPostingUpdateDto.getJobPostingId())
        .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));

    if (jobPosting.getId() != jobPostingUpdateDto.getJobPostingId()){
      throw new CustomException(INVALID_JOB_POSTING_ID);
    }

    if (jobPostingUpdateDto.getPosition() != null) {
      jobPosting.setPosition(jobPostingUpdateDto.getPosition());
    }

    if (jobPostingUpdateDto.getReward() != 0) {
      jobPosting.setReward(jobPostingUpdateDto.getReward());
    }

    if (jobPostingUpdateDto.getDescription() != null) {
      jobPosting.setDescription(jobPostingUpdateDto.getDescription());
    }

    if (jobPostingUpdateDto.getSkill() != null) {
      jobPosting.setSkill(jobPostingUpdateDto.getSkill());
    }

    jobPostingRepository.save(jobPosting);
    return ResponseEntity.ok().body("채용공고가 수정되었습니다.");
  }

  @Override
  public ResponseEntity<String> deleteJobPosting(Long id) {
    if (jobPostingRepository.existsById(id)) {
      jobPostingRepository.deleteById(id);
      return ResponseEntity.ok().body("채용공고가 삭제되었습니다.");
    } else {
      throw new CustomException(JOB_POSTING_NOT_FOUND);
    }
  }

}
