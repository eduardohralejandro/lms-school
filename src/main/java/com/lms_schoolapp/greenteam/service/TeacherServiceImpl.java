package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.model.Teacher;
import com.lms_schoolapp.greenteam.repository.ClassSchoolSubjectRepository;
import com.lms_schoolapp.greenteam.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassSchoolSubjectRepository classSchoolSubjectRepository;

    @Override
    @Transactional
    public void assignTeacherToClass(Teacher teacher, ClassSchoolSubject classSubject) {
        Teacher selectedTeacher = teacherRepository.findById(teacher.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassSchoolSubject selectedClass = classSchoolSubjectRepository.findById(classSubject.getId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        selectedTeacher.getSubjects().add(selectedClass);
        selectedClass.setTeacher(selectedTeacher);
        teacherRepository.save(selectedTeacher);
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAllTeachers();
    }

    @Override
    public List<Teacher> findByFirstNameContainingIgnoreCase(String name) {
        return teacherRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public List<Teacher> findByEmailContainingIgnoreCase(String lastName) {
        return teacherRepository.findByEmailContainingIgnoreCase(lastName);
    }
}
