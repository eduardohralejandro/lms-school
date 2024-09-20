package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Forum;
import com.lms_schoolapp.greenteam.model.ForumType;
import com.lms_schoolapp.greenteam.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {
    private final ForumRepository forumRepository;

    @Override
    public void saveForum(Forum forum) {
        forumRepository.save(forum);
    }

    @Override
    public List<Forum> findForumByDtype(ForumType dtype) {
        return forumRepository.findByForumType(dtype);
    }
}
