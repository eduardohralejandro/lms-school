package com.lms_schoolapp.greenteam.social.service;

import com.lms_schoolapp.greenteam.social.model.Forum;
import com.lms_schoolapp.greenteam.model.ForumType;
import com.lms_schoolapp.greenteam.social.model.ThreadRoom;
import com.lms_schoolapp.greenteam.user.model.User;
import com.lms_schoolapp.greenteam.social.repository.ForumRepository;
import com.lms_schoolapp.greenteam.social.repository.ThreadRoomRepository;
import com.lms_schoolapp.greenteam.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {
    private final UserRepository<? extends User> userRepository;
    private final ForumRepository forumRepository;
    private final ThreadRoomRepository threadRepository;

    @Override
    public void createThread(Long userId, Long forumId, String title) {
        Optional<? extends User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        Optional<Forum> foundForum = forumRepository.findById(forumId);
        if (foundForum.isEmpty()) {
            throw new EntityNotFoundException("Forum not found");
        }

        ThreadRoom forumThread = new ThreadRoom();
        forumThread.setTitle(title);
        forumThread.setUser(foundUser.get());
        forumThread.setForum(foundForum.get());
        forumThread.setCreatedDate(LocalDateTime.now());
        threadRepository.save(forumThread);

    }

    @Override
    public List<ThreadRoom> findAllByForumForumTypeInOrderByCreatedDateAsc(List<ForumType> forumTypes) {
        return threadRepository.findAllByForum_ForumTypeInOrderByCreatedDateAsc(forumTypes);

    }
}
