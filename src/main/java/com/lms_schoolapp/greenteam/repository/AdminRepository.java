package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Admin;
import com.lms_schoolapp.greenteam.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE User a SET a.active = :active")
    void updateAllUsersByActive(Boolean active);

    @Modifying
    @Transactional
    @Query("UPDATE User a SET a.active = :active WHERE a.id = :id")
    void updateById(Long id, boolean active);

    @Query("SELECT  u FROM User u WHERE u.active = false")
    List<User> findByActiveFalse();
}
