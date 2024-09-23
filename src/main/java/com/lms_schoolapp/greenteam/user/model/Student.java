package com.lms_schoolapp.greenteam.user.model;

import com.lms_schoolapp.greenteam.classroom.model.ClassSchoolSubject;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @ManyToMany(mappedBy = "students")
    private Set<ClassSchoolSubject> schoolSubjects = new HashSet<>();
    @Transient
    Long studentCount;
}
