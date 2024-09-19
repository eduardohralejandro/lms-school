package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassSchoolSubjectRepository  extends JpaRepository<ClassSchoolSubject, Long> {
    List<ClassSchoolSubject> findByNameContainingIgnoreCase(String name);
}
