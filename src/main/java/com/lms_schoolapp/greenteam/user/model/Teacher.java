package com.lms_schoolapp.greenteam.user.model;

import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    @OneToMany(mappedBy = "teacher")
    private List<ClassSchoolSubject> subjects = new ArrayList<>();
}
