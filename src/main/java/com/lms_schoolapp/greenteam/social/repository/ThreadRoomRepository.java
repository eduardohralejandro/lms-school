package com.lms_schoolapp.greenteam.social.repository;

import com.lms_schoolapp.greenteam.social.model.ForumType;
import com.lms_schoolapp.greenteam.social.model.ThreadRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRoomRepository extends JpaRepository<ThreadRoom, Long> {
    List<ThreadRoom> findAllByForum_ForumTypeInOrderByCreatedDateAsc(List<ForumType> forumTypes);
}
