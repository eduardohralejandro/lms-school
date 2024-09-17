package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
