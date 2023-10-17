package com.wanted.jobportal.service;

import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostDetailDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.dto.ResponseDto;
import java.util.List;

public interface PostService {

  ResponseDto createPost(PostAddDto postAddDto);
  ResponseDto updatePost(PostUpdateDto postUpdateDto);
  String deletePost(Long postId);
  List<PostListDto> getAllPosts();
  List<PostListDto> searchPosts(String companyName);
  PostDetailDto getPostDetail(Long postId);

}
