package com.lms_schoolapp.greenteam.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {
}
