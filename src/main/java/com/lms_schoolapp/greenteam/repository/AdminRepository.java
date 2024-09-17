package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
