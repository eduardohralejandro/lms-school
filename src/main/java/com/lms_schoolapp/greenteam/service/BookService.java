package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Book;

import java.util.List;

public interface BookService {
    List<Book> fetchAllBooks();
    void saveBook(Book book);
}
