package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<User> findByEmail(String email);
}
