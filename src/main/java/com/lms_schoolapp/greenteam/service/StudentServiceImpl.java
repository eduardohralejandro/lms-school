package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.model.Student;
import com.lms_schoolapp.greenteam.repository.ClassSchoolSubjectRepository;
import com.lms_schoolapp.greenteam.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ClassSchoolSubjectRepository classSchoolSubjectRepository;

    @Override
    @Transactional
    public void assignStudentToClass(Long classId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ClassSchoolSubject classSchoolSubject = classSchoolSubjectRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        student.getSchoolSubjects().add(classSchoolSubject);

        classSchoolSubject.getStudents().add(student);

        studentRepository.save(student);
        classSchoolSubjectRepository.save(classSchoolSubject);
    }

    @Override
    public List<Student> fetchAllStudents() {
        return studentRepository.findAllStudents();
    }

    @Override
    public List<Student> findByFirstNameContainingIgnoreCase(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> findByEmailContainingIgnoreCase(String email) {
        return studentRepository.findByEmailContainingIgnoreCase(email);
    }
}
