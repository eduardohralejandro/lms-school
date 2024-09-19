package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
