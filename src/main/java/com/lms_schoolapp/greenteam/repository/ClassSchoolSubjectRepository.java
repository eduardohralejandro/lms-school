package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClassSchoolSubjectRepository extends JpaRepository<ClassSchoolSubject, Long> {
    List<ClassSchoolSubject> findByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(s.id) FROM ClassSchoolSubject c " +
            "JOIN c.students s " +
            "WHERE c.id = :classId")
    long countStudentsInClass(@Param("classId") Long classId);

    @Query("SELECT c FROM ClassSchoolSubject c JOIN c.teacher t WHERE t.id = :teacherId")
    List<ClassSchoolSubject> findClassesWithTeacher(@Param("teacherId") Long teacherId);

    @Query("SELECT c.id, COUNT(s) FROM ClassSchoolSubject c JOIN c.students s WHERE c.id IN :classIds GROUP BY c.id")
    List<Object[]> countStudentsInClasses(@Param("classIds") Set<Long> classIds);
}
