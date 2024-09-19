package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.BookService;
import com.lms_schoolapp.greenteam.service.ClassSchoolSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeacherDashboard {
    private final ClassSchoolSubjectService classSchoolSubjectService;
    private final BookService bookService;

    public void start(User loggedInUser) {
        printWelcomeMessage();
        System.out.printf("Welcome back: %s %s \n", loggedInUser.getFirstName(), loggedInUser.getLastName());
        selectOptionTeacher(loggedInUser);
    }

    public void printWelcomeMessage() {
        System.out.println("*****************");
        System.out.println("Teacher Dashboard");
        System.out.println("*****************");
    }

    public void selectOptionTeacher(User loggedInUser) {
        System.out.println("Select operation ");
        boolean continueSelection = false;
        while (!continueSelection) {
            TeacherMainMenuOption option = (TeacherMainMenuOption) KeyboardUtility.askForElementInArray(TeacherMainMenuOption.values());
            switch (option) {
                case DISPLAY_CLASSES_TEACHER -> startDisplayClasses(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startDisplayClasses(User loggedInUser) {
        List<ClassSchoolSubject> classSchoolSubjectList = classSchoolSubjectService.findClassesWithTeacher(loggedInUser.getId());
        if (classSchoolSubjectList.isEmpty()) {
            System.out.printf("No classes found associated to: %s were found\n", loggedInUser.getFirstName());
        } else {
            System.out.printf("Select a book to manage it. Professor %s has been assigned to these classes: \n", loggedInUser.getFirstName());
            ClassSchoolSubject classes = (ClassSchoolSubject) KeyboardUtility.askForElementInArray(classSchoolSubjectList.toArray());
            selectBookIn(classes);
        }
    }

    public void selectBookIn(ClassSchoolSubject classSchoolSubject) {
        System.out.println("Select operation to carry on with this class");
        boolean continueSelection = false;
        while (!continueSelection) {
            TeacherClassOption option = (TeacherClassOption) KeyboardUtility.askForElementInArray(TeacherClassOption.values());
            switch (option) {
                case ADD_BOOK_TO_CLASS -> assignBookToClass(classSchoolSubject);
                case REMOVE_BOOK_FROM_CLASS -> removeBookFromClass(classSchoolSubject);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void assignBookToClass(ClassSchoolSubject classSchoolSubject) {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found");
        } else {
            Book book = (Book) KeyboardUtility.askForElementInArray(books.toArray());
            classSchoolSubjectService.assignBookToClass(classSchoolSubject.getId(), book.getId());
            System.out.printf("%s was successfully added to the class: %s", book.getTitle(), classSchoolSubject.getName());
        }
    }

    public void removeBookFromClass(ClassSchoolSubject classSchoolSubject) {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found");
        } else {
            Book book = (Book) KeyboardUtility.askForElementInArray(books.toArray());
            classSchoolSubjectService.removeBookFromClass(classSchoolSubject.getId(), book.getId());
            System.out.printf("%s was successfully removed from class: %s", book.getTitle(), classSchoolSubject.getName());
        }
    }
}
