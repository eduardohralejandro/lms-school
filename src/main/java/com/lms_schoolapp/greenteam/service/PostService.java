package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Post;

import java.util.List;

public interface PostService {
    void createPost(Post post, Long userId, Long threadId);
    List<Post> findAllPostsByThreadId(Long threadId);
}
