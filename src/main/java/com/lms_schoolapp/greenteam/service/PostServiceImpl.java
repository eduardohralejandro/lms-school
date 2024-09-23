package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Post;
import com.lms_schoolapp.greenteam.model.ThreadRoom;
import com.lms_schoolapp.greenteam.user.model.User;
import com.lms_schoolapp.greenteam.repository.PostRepository;
import com.lms_schoolapp.greenteam.repository.ThreadRoomRepository;
import com.lms_schoolapp.greenteam.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository<? extends User> userRepository;
    private final ThreadRoomRepository threadRoomRepository;

    @Override
    public void createPost(Post post, Long userId, Long threadId) {
        Optional<? extends User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        Optional<ThreadRoom> threadRoom = threadRoomRepository.findById(threadId);
        if (threadRoom.isEmpty()) {
            throw new EntityNotFoundException("Thread not found");
        }

        post.setUser(foundUser.get());
        post.setThread(threadRoom.get());
        postRepository.save(post);
    }

    @Override
    public List<Post> findAllPostsByThreadId(Long threadId) {
        return postRepository.findAllPostsByThreadId(threadId);
    }
}
