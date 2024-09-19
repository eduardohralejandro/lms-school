package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.repository.ClassSchoolSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSchoolSubjectServiceImpl implements ClassSchoolSubjectService {
    private final ClassSchoolSubjectRepository classSchoolSubjectRepository;

    @Override
    public void save(ClassSchoolSubject classSchoolSubject) {
        classSchoolSubject.setStartDate(LocalDateTime.now());
        classSchoolSubjectRepository.save(classSchoolSubject);
    }

    @Override
    public List<ClassSchoolSubject> findAll() {
        return classSchoolSubjectRepository.findAll();
    }
}
