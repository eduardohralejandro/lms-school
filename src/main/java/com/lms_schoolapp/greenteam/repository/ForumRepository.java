package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Forum;
import com.lms_schoolapp.greenteam.model.ForumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    @Query("SELECT f FROM Forum f WHERE f.forumType = :forumType OR f.forumType = 'GENERAL'")
    List<Forum> findByForumType(ForumType forumType);
}
