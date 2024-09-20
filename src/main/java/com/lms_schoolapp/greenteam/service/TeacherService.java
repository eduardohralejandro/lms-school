package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.model.Teacher;

import java.util.List;

public interface TeacherService {
    void assignTeacherToClass(Teacher selectedTeacher, ClassSchoolSubject selectedClass);
    List<Teacher> findAllTeachers();
    List<Teacher> findByFirstNameContainingIgnoreCase(String name);
    List<Teacher> findByEmailContainingIgnoreCase(String lastName);
    List<Teacher> getTeachersWithClassDetails();
    List<Teacher> findAllTeachersWithClasses(Long studentId);
}
