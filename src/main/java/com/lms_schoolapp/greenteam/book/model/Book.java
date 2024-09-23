package com.lms_schoolapp.greenteam.book.model;

import com.lms_schoolapp.greenteam.classroom.model.ClassSchoolSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String edition;
    @ManyToOne
    @JoinColumn(name = "class_subject_id")
    private ClassSchoolSubject subject;
    @Override
    public String toString() {
        return "Book title " + title + " author " + author + " isbn " + isbn + " edition " + edition;
    }
}
