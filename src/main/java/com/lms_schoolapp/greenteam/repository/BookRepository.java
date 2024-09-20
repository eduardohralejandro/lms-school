package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String name);

    List<Book> findByAuthorContainingIgnoreCase(String authorName);

    @Query("SELECT b FROM Book b JOIN b.subject c JOIN c.students s WHERE c.id = :classId AND s.id = :userId")
    Book findMandatoryBooksPerClassAndUser(@Param("userId") Long userId, @Param("classId") Long classId);
}
