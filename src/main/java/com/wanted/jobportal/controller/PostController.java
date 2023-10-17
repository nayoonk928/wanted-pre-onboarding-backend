package com.wanted.jobportal.controller;

import com.wanted.jobportal.dto.PostAddDto;
import com.wanted.jobportal.dto.PostDetailDto;
import com.wanted.jobportal.dto.PostListDto;
import com.wanted.jobportal.dto.PostUpdateDto;
import com.wanted.jobportal.dto.ResponseDto;
import com.wanted.jobportal.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<ResponseDto> createPost(@RequestBody PostAddDto dto) {
    return ResponseEntity.ok().body(postService.createPost(dto));
  }

  @PatchMapping
  public ResponseEntity<ResponseDto> updatePost(@RequestBody PostUpdateDto dto) {
    return ResponseEntity.ok().body(postService.updatePost(dto));
  }

  @DeleteMapping
  public ResponseEntity<String> deletePost(@PathVariable Long postId) {
    return ResponseEntity.ok().body(postService.deletePost(postId));
  }

  @GetMapping
  public ResponseEntity<List<PostListDto>> getAllPosts() {
    return ResponseEntity.ok().body(postService.getAllPosts());
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<List<PostListDto>> searchPosts(@PathVariable String keyword) {
    return ResponseEntity.ok().body(postService.searchPosts(keyword));
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable Long postId) {
    return ResponseEntity.ok().body(postService.getPostDetail(postId));
  }

}