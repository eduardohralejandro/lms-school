package com.lms_schoolapp.greenteam.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

}
