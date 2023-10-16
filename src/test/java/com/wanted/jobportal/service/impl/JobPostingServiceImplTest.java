package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_JOB_POSTING_ID;
import static com.wanted.jobportal.exception.ErrorCode.JOB_POSTING_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.JobPosting;
import com.wanted.jobportal.dto.JobPostingDto;
import com.wanted.jobportal.dto.JobPostingUpdateDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.JobPostingRepository;
import com.wanted.jobportal.service.JobPostingService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class JobPostingServiceImplTest {

  private JobPostingService jobPostingService;

  private CompanyRepository companyRepository;

  private JobPostingRepository jobPostingRepository;

  @BeforeEach
  void setUp() {
    companyRepository = mock(CompanyRepository.class);
    jobPostingRepository = mock(JobPostingRepository.class);
    jobPostingService = new JobPostingServiceImpl(companyRepository, jobPostingRepository);
  }

  @Test
  @DisplayName("채용공고 등록 - 성공")
  void createJobPosting_Success() {
    //given
    Company company = Company.builder()
        .name("원티드랩")
        .country("한국")
        .region("서울")
        .build();

    JobPostingDto jobPostingDto = JobPostingDto.builder()
        .companyId(1L)
        .position("백엔드 주니어 개발자")
        .reward(1500000)
        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
        .skill("Python")
        .build();

    //when
    when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
    ResponseEntity<String> response = jobPostingService.createJobPosting(jobPostingDto);

    //then
    verify(companyRepository, times(1)).findById(1L);
    verify(jobPostingRepository, times(1)).save(any());
    assertEquals(response.getBody(), "채용공고가 등록되었습니다.");
  }

  @Test
  @DisplayName("채용공고 등록 - 실패")
  void createJobPosting_CompanyNotFound() {
    //given
    JobPostingDto jobPostingDto = JobPostingDto.builder()
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
        () -> jobPostingService.createJobPosting(jobPostingDto));
    assertEquals(COMPANY_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 수정 - 성공")
  void updateJobPosting_Success() {
    //given
    JobPostingUpdateDto updateDto = JobPostingUpdateDto.builder()
        .jobPostingId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    JobPosting existingJobPosting = JobPosting.builder()
        .id(1L)
        .position("기존 포지션")
        .reward(1000000)
        .description("기존 설명")
        .skill("Python")
        .build();

    //when
    when(jobPostingRepository.findById(1L)).thenReturn(java.util.Optional.of(existingJobPosting));
    ResponseEntity<String> response = jobPostingService.updateJobPosting(updateDto);

    //then
    verify(jobPostingRepository, times(1)).findById(1L);
    verify(jobPostingRepository, times(1)).save(any());
    assertEquals("채용공고가 수정되었습니다.", response.getBody());
    assertEquals("업데이트된 포지션", existingJobPosting.getPosition());
    assertEquals(2000000, existingJobPosting.getReward());
    assertEquals("업데이트된 설명", existingJobPosting.getDescription());
    assertEquals("Java", existingJobPosting.getSkill());
  }

  @Test
  @DisplayName("채용공고 수정 - 실패")
  void updateJobPosting_PostingNotFound() {
    //given
    JobPostingUpdateDto updateDto = JobPostingUpdateDto.builder()
        .jobPostingId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    //when
    when(jobPostingRepository.findById(1L)).thenReturn(java.util.Optional.empty());

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> jobPostingService.updateJobPosting(updateDto));
    assertEquals(JOB_POSTING_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 수정 - 실패")
  void updateJobPosting_FailWithModifiedJobPostingId() {
    //given
    JobPostingUpdateDto updateDto = JobPostingUpdateDto.builder()
        .jobPostingId(1L)
        .position("업데이트된 포지션")
        .reward(2000000)
        .description("업데이트된 설명")
        .skill("Java")
        .build();

    JobPosting existingJobPosting = JobPosting.builder()
        .id(2L)
        .position("기존 포지션")
        .reward(1000000)
        .description("기존 설명")
        .skill("Python")
        .build();


    //when
    when(jobPostingRepository.findById(1L)).thenReturn(java.util.Optional.of(existingJobPosting));

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> jobPostingService.updateJobPosting(updateDto));
    assertEquals(INVALID_JOB_POSTING_ID, exception.getErrorCode());
  }

  @Test
  @DisplayName("채용공고 삭제 - 성공")
  void deleteJobPosting_Success() {
    //given
    Long jobPostingId = 1L;

    //when
    when(jobPostingRepository.existsById(jobPostingId)).thenReturn(true);
    ResponseEntity<String> response = jobPostingService.deleteJobPosting(jobPostingId);

    //then
    verify(jobPostingRepository, times(1)).existsById(jobPostingId);
    verify(jobPostingRepository, times(1)).deleteById(jobPostingId);
    assertEquals("채용공고가 삭제되었습니다.", response.getBody());
  }

  @Test
  @DisplayName("채용공고 삭제 - 실패")
  void deleteJobPosting_PostingNotFound() {
    //given
    Long jobPostingId = 1L;

    //when
    when(jobPostingRepository.existsById(jobPostingId)).thenReturn(false);

    //then
    CustomException exception = assertThrows(CustomException.class,
        () -> jobPostingService.deleteJobPosting(jobPostingId));
    assertEquals(JOB_POSTING_NOT_FOUND, exception.getErrorCode());
  }

}