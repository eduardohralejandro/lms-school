package com.lms_schoolapp.greenteam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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
