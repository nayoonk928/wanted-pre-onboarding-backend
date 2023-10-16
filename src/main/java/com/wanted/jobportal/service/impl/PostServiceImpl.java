package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_JOB_POSTING_ID;
import static com.wanted.jobportal.exception.ErrorCode.JOB_POSTING_NOT_FOUND;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.PostRepository;
import com.wanted.jobportal.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final CompanyRepository companyRepository;
  private final PostRepository postRepository;

  @Override
  @Transactional
  public ResponseEntity<String> createJobPosting(PostAddDto postAddDto) {
    Company company = companyRepository.findById(postAddDto.getCompanyId())
        .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

    Post post = Post.builder()
        .company(company)
        .position(postAddDto.getPosition())
        .reward(postAddDto.getReward())
        .description(postAddDto.getDescription())
        .skill(postAddDto.getSkill())
        .build();

    postRepository.save(post);
    return ResponseEntity.ok().body("채용공고가 등록되었습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> updateJobPosting(PostUpdateDto postUpdateDto) {
    Post post = postRepository.findById(postUpdateDto.getJobPostingId())
        .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));

    if (post.getId() != postUpdateDto.getJobPostingId()){
      throw new CustomException(INVALID_JOB_POSTING_ID);
    }

    if (postUpdateDto.getPosition() != null) {
      post.setPosition(postUpdateDto.getPosition());
    }

    if (postUpdateDto.getReward() != 0) {
      post.setReward(postUpdateDto.getReward());
    }

    if (postUpdateDto.getDescription() != null) {
      post.setDescription(postUpdateDto.getDescription());
    }

    if (postUpdateDto.getSkill() != null) {
      post.setSkill(postUpdateDto.getSkill());
    }

    postRepository.save(post);
    return ResponseEntity.ok().body("채용공고가 수정되었습니다.");
  }

  @Override
  @Transactional
  public ResponseEntity<String> deleteJobPosting(Long id) {
    if (postRepository.existsById(id)) {
      postRepository.deleteById(id);
      return ResponseEntity.ok().body("채용공고가 삭제되었습니다.");
    } else {
      throw new CustomException(JOB_POSTING_NOT_FOUND);
    }
  }

}