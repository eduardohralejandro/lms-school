package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT  u FROM Student u")
    List<Student> findAllStudents();

    List<Student> findByFirstNameContainingIgnoreCase(String name);

    List<Student> findByEmailContainingIgnoreCase(String email);

    @Query("SELECT s FROM Student s JOIN s.schoolSubjects c JOIN c.teacher t WHERE c.id = :classId AND t.id = :teacherId")
    List<Student> findStudentsByClassAndTeacher(@Param("classId") Long classId, @Param("teacherId") Long teacherId);

    @Query("SELECT COUNT(s) FROM Student s JOIN s.schoolSubjects c JOIN c.teacher t WHERE c.id = :classId AND t.id = :teacherId")
    Long countStudentsByClassAndTeacher(@Param("classId") Long classId, @Param("teacherId") Long teacherId);

}
