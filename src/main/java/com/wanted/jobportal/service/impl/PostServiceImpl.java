package com.wanted.jobportal.service.impl;

import static com.wanted.jobportal.exception.ErrorCode.COMPANY_NOT_FOUND;
import static com.wanted.jobportal.exception.ErrorCode.INVALID_POST_ID;
import static com.wanted.jobportal.exception.ErrorCode.POST_NOT_FOUND;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostDetailDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.exception.CustomException;
import com.wanted.jobportal.repository.CompanyRepository;
import com.wanted.jobportal.repository.PostRepository;
import com.wanted.jobportal.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final CompanyRepository companyRepository;
  private final PostRepository postRepository;

  @Override
  @Transactional
  public String createPost(PostAddDto postAddDto) {
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
    return "채용공고가 등록되었습니다.";
  }

  @Override
  @Transactional
  public String updatePost(PostUpdateDto postUpdateDto) {
    Post post = postRepository.findById(postUpdateDto.getPostId())
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    if (post.getId() != postUpdateDto.getPostId()){
      throw new CustomException(INVALID_POST_ID);
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
    return "채용공고가 수정되었습니다.";
  }

  @Override
  @Transactional
  public String deletePost(Long postId) {
    if (postRepository.existsById(postId)) {
      postRepository.deleteById(postId);
      return "채용공고가 삭제되었습니다.";
    } else {
      throw new CustomException(POST_NOT_FOUND);
    }
  }

  @Override
  public List<PostListDto> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    List<PostListDto> postListDtos = posts.stream().map(PostListDto::of).toList();
    return postListDtos;
  }

  public List<PostListDto> searchPosts(String keyword) {
    List<Post> posts = postRepository.searchPosts(keyword);
    List<PostListDto> postListDtos = posts.stream().map(PostListDto::of).toList();
    return postListDtos;
  }

  @Override
  public PostDetailDto getPostDetail(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    return PostDetailDto.from(post, post.getCompany().getPosts());
  }

}
