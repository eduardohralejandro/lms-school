package com.lms_schoolapp.greenteam.social.repository;

import com.lms_schoolapp.greenteam.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByThreadId(Long threadId);
}
