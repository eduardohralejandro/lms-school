package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String name);
    List<Book> findByAuthorContainingIgnoreCase(String authorName);
}
