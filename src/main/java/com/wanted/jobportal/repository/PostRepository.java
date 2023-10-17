package com.wanted.jobportal.repository;

import com.wanted.jobportal.domain.Post;
import com.wanted.jobportal.dto.PostListDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("SELECT p FROM post p " +
      "WHERE p.company.name LIKE %:keyword% " +
      "OR p.position LIKE %:keyword% " +
      "OR p.skill LIKE %:keyword% " +
      "OR p.company.country LIKE %:keyword% " +
      "OR p.company.region LIKE %:keyword%")
  List<Post> searchPosts(@Param("keyword") String keyword);

}
