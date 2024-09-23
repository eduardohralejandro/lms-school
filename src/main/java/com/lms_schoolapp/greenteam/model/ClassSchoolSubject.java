package com.lms_schoolapp.greenteam.model;

import com.lms_schoolapp.greenteam.user.model.Student;
import com.lms_schoolapp.greenteam.user.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "class_school_subject")
public class ClassSchoolSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 1, max = 500)
    private String name;
    @Column(nullable = false)
    @Size(min = 1, max = 500)
    private String description;
    @Column(nullable = false, name = "start_date")
    LocalDateTime startDate;
    @Column(name = "end_date")
    LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    Teacher teacher;
    @ManyToMany
    @JoinTable(
            name = "student_schoolsubject",
            joinColumns = @JoinColumn(name = "school_subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();
    @Transient
    private Long studentCount;
    @OneToMany(mappedBy = "subject")
    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return "Name: " + name + ". Description: " + description + " start: " + startDate + " end: " + endDate;
    }
}