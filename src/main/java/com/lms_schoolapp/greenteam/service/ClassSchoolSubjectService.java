package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;

import java.util.List;

public interface ClassSchoolSubjectService {
    void save(ClassSchoolSubject classSchoolSubject);
    List<ClassSchoolSubject> findAll();
    List<ClassSchoolSubject> findClassesWithTeacher(Long teacherId);
    void assignBookToClass(Long classId, Long bookId);
    void removeBookFromClass(Long classId, Long bookId);
}
