package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Book;
import com.lms_schoolapp.greenteam.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.repository.BookRepository;
import com.lms_schoolapp.greenteam.repository.ClassSchoolSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSchoolSubjectServiceImpl implements ClassSchoolSubjectService {
    private final ClassSchoolSubjectRepository classSchoolSubjectRepository;
    private final BookRepository bookRepository;

    @Override
    public void save(ClassSchoolSubject classSchoolSubject) {
        classSchoolSubject.setStartDate(LocalDateTime.now());
        classSchoolSubjectRepository.save(classSchoolSubject);
    }

    @Override
    public List<ClassSchoolSubject> findAll() {
        return classSchoolSubjectRepository.findAll();
    }

    @Override
    public List<ClassSchoolSubject> findClassesWithTeacher(Long teacherId) {
        return classSchoolSubjectRepository.findClassesWithTeacher(teacherId);
    }

    @Override
    @Transactional
    public void assignBookToClass(Long classId, Long bookId) {
        ClassSchoolSubject selectedSubject = classSchoolSubjectRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Book selectedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        selectedSubject.getBooks().add(selectedBook);

        selectedBook.setSubject(selectedSubject);

        classSchoolSubjectRepository.save(selectedSubject);
        bookRepository.save(selectedBook);
    }

    @Override
    @Transactional
    public void removeBookFromClass(Long classId, Long bookId) {
        ClassSchoolSubject selectedSubject = classSchoolSubjectRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Book selectedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        selectedSubject.getBooks().remove(selectedBook);

        selectedBook.setSubject(null);

        classSchoolSubjectRepository.save(selectedSubject);
        bookRepository.save(selectedBook);
    }

    @Override
    public List<ClassSchoolSubject> getClassesForStudent(Long studentId) {
        return classSchoolSubjectRepository.getClassesForStudent(studentId);
    }

}
