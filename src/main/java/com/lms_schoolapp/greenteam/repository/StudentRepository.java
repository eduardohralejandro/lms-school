package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT  u FROM Student u")
    List<Student> findAllStudents();

    List<Student> findByFirstNameContainingIgnoreCase(String name);

    List<Student> findByEmailContainingIgnoreCase(String email);
}
