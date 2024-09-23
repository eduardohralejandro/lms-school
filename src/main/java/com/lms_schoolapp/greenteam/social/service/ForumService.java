package com.lms_schoolapp.greenteam.social.service;

import com.lms_schoolapp.greenteam.social.model.Forum;
import com.lms_schoolapp.greenteam.model.ForumType;

import java.util.List;

public interface ForumService {
    void saveForum(Forum forum);
    List<Forum> findForumByDtype(ForumType dtype);
}
