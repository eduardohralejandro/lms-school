package com.lms_schoolapp.greenteam.book.service;

import com.lms_schoolapp.greenteam.book.model.Book;

import java.util.List;

public interface BookService {
    List<Book> fetchAllBooks();
    void saveBook(Book book);
    void deleteById(Long id);
    void updateBookById(Book book);
    List<Book> findByTitleContainingIgnoreCase(String name);
    List<Book> findByAuthorContainingIgnoreCase(String authorName);
    Book findMandatoryBooksPerClassAndUser(Long userId, Long classId);
}
