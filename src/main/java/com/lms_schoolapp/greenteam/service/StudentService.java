package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Student;
import com.lms_schoolapp.greenteam.model.Teacher;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentService {
    void assignStudentToClass(Long classId, Long studentId);

    List<Student> fetchAllStudents();
    List<Student> findByFirstNameContainingIgnoreCase(String name);
    List<Student> findByEmailContainingIgnoreCase(String email);
}
