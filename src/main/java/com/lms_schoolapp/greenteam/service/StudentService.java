package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentService {
    void assignStudentToClass(Long classId, Long studentId);

    List<Student> fetchAllStudents();
}
