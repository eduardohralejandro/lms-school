package com.lms_schoolapp.greenteam.user.repository;

import com.lms_schoolapp.greenteam.user.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT  u FROM Teacher u")
    List<Teacher> findAllTeachers();

    List<Teacher> findByFirstNameContainingIgnoreCase(String name);

    List<Teacher> findByEmailContainingIgnoreCase(String email);

    @Query("SELECT t FROM Teacher t " +
            "JOIN t.subjects c " +
            "GROUP BY t.id, c.id")
    List<Teacher> findAllTeachersWithClasses();

    @Query("SELECT t FROM Teacher t JOIN FETCH t.subjects c JOIN c.students s WHERE s.id = :studentId")
    List<Teacher> findAllTeachersAssociatedWithClassesOfStudent(@Param("studentId") Long studentId);

}
