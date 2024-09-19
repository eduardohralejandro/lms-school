package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Book;
import com.lms_schoolapp.greenteam.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> fetchAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBookById(Book updatedBook) {
        Book existingBook = bookRepository.findById(updatedBook.getId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + updatedBook.getId()));
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setEdition(updatedBook.getEdition());

        bookRepository.save(existingBook);
    }
}