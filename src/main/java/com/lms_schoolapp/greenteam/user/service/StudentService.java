package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.user.model.Student;

import java.util.List;

public interface StudentService {
    void assignStudentToClass(Long classId, Long studentId);

    List<Student> fetchAllStudents();
    List<Student> findByFirstNameContainingIgnoreCase(String name);
    List<Student> findByEmailContainingIgnoreCase(String email);
    List<Student> findStudentsByClassAndTeacher( Long classId,  Long teacherId);
}
