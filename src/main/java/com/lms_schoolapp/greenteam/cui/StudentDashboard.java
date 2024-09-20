package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.BookService;
import com.lms_schoolapp.greenteam.service.ClassSchoolSubjectService;
import com.lms_schoolapp.greenteam.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentDashboard {
    private final ClassSchoolSubjectService classSchoolSubjectService;
    private final BookService bookService;
    private final TeacherService teacherService;

    public void start(User loggedInUser) {
        printWelcomeMessage();
        System.out.printf("Welcome back: %s %s \n", loggedInUser.getFirstName(), loggedInUser.getLastName());
        selectOptionStudent(loggedInUser);
    }

    public void printWelcomeMessage() {
        System.out.println("*****************");
        System.out.println("Student Dashboard");
        System.out.println("*****************");
    }

    public void selectOptionStudent(User loggedInUser) {
        System.out.println("Select operation ");
        boolean continueSelection = false;
        while (!continueSelection) {
            StudentMainMenuOption option = (StudentMainMenuOption) KeyboardUtility.askForElementInArray(StudentMainMenuOption.values());
            switch (option) {
                case DISPLAY_CLASSES_ASSIGNED -> startDisplayClassesAssigned(loggedInUser);
                case DISPLAY_MANDATORY_BOOKS_PER_CLASS -> startDisplayMandatoryBooks(loggedInUser);
                case DISPLAY_TEACHERS_ASSIGNED -> startDisplayTeacherAssigned(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startDisplayTeacherAssigned(User loggedInUser) {
        List<Teacher> teachers = teacherService.findAllTeachersWithClasses(loggedInUser.getId());
        System.out.printf("Student %s is assigned to the following teachers: \n", loggedInUser.getFirstName());
        teachers.forEach(this::displayTeacherDetail);
    }

    private void displayTeacherDetail(Teacher teacher) {
        System.out.printf("Teacher: %s %s, email:%s \n", teacher.getFirstName(), teacher.getLastName(), teacher.getEmail());
        teacher.getSubjects().forEach(subject -> {
            System.out.printf("Subject: %s\n", subject.getName());
        });
    }

    private void startDisplayMandatoryBooks(User loggedInUser) {
        List<ClassSchoolSubject> classSchoolSubjectList = classSchoolSubjectService.getClassesForStudent(loggedInUser.getId());

        classSchoolSubjectList.forEach(classSchoolSubject -> {
            Book booksAssigned = bookService.findMandatoryBooksPerClassAndUser(loggedInUser.getId(), classSchoolSubject.getId());

            if (booksAssigned != null) {
                System.out.printf("Book: %s, Class: %s%n", booksAssigned.getTitle(), classSchoolSubject.getName());
            } else {
                System.out.printf("No mandatory books for Class: %s%n", classSchoolSubject.getName());
            }
        });

    }

    private void startDisplayClassesAssigned(User loggedInUser) {
        List<ClassSchoolSubject> classSchoolSubjectList = classSchoolSubjectService.getClassesForStudent(loggedInUser.getId());
        if (classSchoolSubjectList.isEmpty()) {
            System.out.println("You are not subscribed to any class yet");
        } else {
            classSchoolSubjectList.forEach(this::displayDetailClass);
        }

    }

    private void displayDetailClass(ClassSchoolSubject classSchoolSubject) {
        System.out.printf("Class you are subscribed: %s, start: %s , end:%s\n ", classSchoolSubject.getName(), classSchoolSubject.getStartDate().toString(), classSchoolSubject.getEndDate().toString());
    }
}
