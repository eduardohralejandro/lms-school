package com.lms_schoolapp.greenteam.social.repository;

import com.lms_schoolapp.greenteam.social.model.Forum;
import com.lms_schoolapp.greenteam.social.model.ForumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    @Query("SELECT f FROM Forum f WHERE f.forumType = :forumType OR f.forumType = 'GENERAL'")
    List<Forum> findByForumType(ForumType forumType);
}
