package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.user.model.Student;
import com.lms_schoolapp.greenteam.repository.ClassSchoolSubjectRepository;
import com.lms_schoolapp.greenteam.user.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public List<Student> findStudentsByClassAndTeacher(Long classId, Long teacherId) {
        List<Student> students = studentRepository.findStudentsByClassAndTeacher(classId, teacherId);

        Set<Long> classIds = new HashSet<>();
        for (Student student : students) {
            for (ClassSchoolSubject classSubject : student.getSchoolSubjects()) {
                classIds.add(classSubject.getId());
            }
        }

        List<Object[]> studentCountsRaw = classSchoolSubjectRepository.countStudentsInClasses(classIds);

        Map<Long, Long> studentCounts = studentCountsRaw.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        for (Student student : students) {
            for (ClassSchoolSubject classSubject : student.getSchoolSubjects()) {
                Long studentCount = studentCounts.get(classSubject.getId());
                classSubject.setStudentCount(studentCount != null ? studentCount : 0L); // Handle null case
            }
        }

        return students;
    }
}
