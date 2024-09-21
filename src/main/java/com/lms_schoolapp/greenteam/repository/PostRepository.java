package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByThreadId(Long threadId);
}
