package com.wanted.jobportal.service;

import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import java.util.List;

public interface PostService {

  String createJobPosting(PostAddDto postAddDto);
  String updateJobPosting(PostUpdateDto postUpdateDto);
  String deleteJobPosting(Long id);
  List<PostListDto> getAllPosts();

}
