package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ForumType;
import com.lms_schoolapp.greenteam.model.ThreadRoom;

import java.util.List;

public interface ThreadService {
    void createThread(Long userId, Long forumId, String title);

    List<ThreadRoom> findAllByForumForumTypeInOrderByCreatedDateAsc(List<ForumType> forumTypes);
}
