package com.lms_schoolapp.greenteam.social.service;

import com.lms_schoolapp.greenteam.social.model.Post;

import java.util.List;

public interface PostService {
    void createPost(Post post, Long userId, Long threadId);
    List<Post> findAllPostsByThreadId(Long threadId);
}
