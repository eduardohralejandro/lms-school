package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.classroom.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.user.model.Teacher;

import java.util.List;

public interface TeacherService {
    void assignTeacherToClass(Teacher selectedTeacher, ClassSchoolSubject selectedClass);
    List<Teacher> findAllTeachers();
    List<Teacher> findByFirstNameContainingIgnoreCase(String name);
    List<Teacher> findByEmailContainingIgnoreCase(String lastName);
    List<Teacher> getTeachersWithClassDetails();
    List<Teacher> findAllTeachersWithClasses(Long studentId);
}
