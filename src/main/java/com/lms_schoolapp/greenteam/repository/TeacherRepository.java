package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Student;
import com.lms_schoolapp.greenteam.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT  u FROM Teacher u")
    List<Teacher> findAllTeachers();

    List<Teacher> findByFirstNameContainingIgnoreCase(String name);

    List<Teacher> findByEmailContainingIgnoreCase(String email);

}
