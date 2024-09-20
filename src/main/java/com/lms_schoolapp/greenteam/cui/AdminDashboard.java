package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.repository.ForumRepository;
import com.lms_schoolapp.greenteam.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lms_schoolapp.greenteam.model.FilterOption.CONTINUE;

@Component
@RequiredArgsConstructor
public class AdminDashboard {
    private final AdminService adminService;
    private final UserService<Admin> userAdminService;
    private final ClassSchoolSubjectService classSchoolSubjectService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final BookService bookService;
    private final ForumService forumService;

    public void start(User loggedInUser) {
        printWelcomeMessage();
        System.out.printf("Welcome back: %s %s \n", loggedInUser.getFirstName(), loggedInUser.getLastName());
        printOptionAdminDashboard((Admin) loggedInUser);
    }

    public void printWelcomeMessage() {
        System.out.println("***************");
        System.out.println("Admin Dashboard");
        System.out.println("***************");
    }

    public void printOptionAdminDashboard(Admin loggedInAdmin) {
        boolean continueSelection = false;
        while (!continueSelection) {
            AdminMenuOption option = KeyboardUtility.askForElementInArray(AdminMenuOption.values());
            switch (option) {
                case ACTIVE_USER -> activeUser();
                case REGISTER_ADMIN -> startAdminRegistration();
                case ACTIVE_USERS -> activeUsers();
                case DISPLAY_INACTIVE_USERS -> displayInactiveUsers();
                case CREATE_CLASS_SUBJECT -> startCreateClassSubject();
                case DISPLAY_ALL_SUBJECTS -> startDisplaySubjects();
                case ASSIGN_CLASS -> selectClassesOption();
                case DISPLAY_TEACHER_DETAIL -> startDisplayTeacherClasses();
                case DISPLAY_ALL_BOOKS -> displayAllBooks();
                case ADD_BOOK -> startBookCreation();
                case UPDATE_BOOK_DELETE -> selectOperationOnBook();
                case SEARCH_BOOK -> searchBook();
                case CREATE_FORUM -> createForum();
                case EXIT -> {
                    loggedInAdmin = null;
                    continueSelection = true;
                }
            }
        }
    }

    private void createForum() {
        System.out.println("Create forum, here you can create type of forums, either STUDENT, ADMIN, TEACHER or GENERAL");
        ForumType forumType = askForForumType();
        Forum forum = new Forum();
        forum.setForumType(forumType);
        forumService.saveForum(forum);
    }

    private ForumType askForForumType() {
        return KeyboardUtility.askForElementInArray(ForumType.values());
    }

    private void startCreateClassSubject() {
        System.out.println("Create class subject");
        ClassSchoolSubject classSchoolSubject = buildSubject();
        classSchoolSubjectService.save(classSchoolSubject);
        System.out.printf("New class subject created: %s\n", classSchoolSubject.getName());
    }

    private ClassSchoolSubject buildSubject() {
        String subjectName = askForSubjectName();
        String description = askForDescription();
        String startDate = askForStartDate();
        String endDate = askForEndDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ClassSchoolSubject newClass = new ClassSchoolSubject();
        newClass.setName(subjectName);
        newClass.setDescription(description);
        newClass.setStartDate(LocalDate.parse(startDate, formatter).atStartOfDay());
        newClass.setEndDate(LocalDate.parse(endDate, formatter).atStartOfDay());
        return newClass;
    }

    public String askForSubjectName() {
        return KeyboardUtility.askForString("Enter subject name: ");
    }

    public String askForDescription() {
        return KeyboardUtility.askForString("Enter subject description: ");
    }

    public String askForStartDate() {
        return KeyboardUtility.askForString("Enter startDate (format: yyyy-MM-dd): ");
    }

    public String askForEndDate() {
        return KeyboardUtility.askForString("Enter startDate (format: yyyy-MM-dd): ");
    }


    public void displayInactiveUsers() {
        System.out.println("**********************");
        System.out.println("List of inactive users");
        System.out.println("**********************");
        List<User> allUsers = adminService.findAllInactiveUsers();
        if (!allUsers.isEmpty()) {
            allUsers.forEach(this::displayDetailUser);
        } else {
            System.out.println("There are no inactive users");
        }

    }

    private void displayDetailUser(User user) {
        System.out.printf("User %s %s is not active\n", user.getFirstName(), user.getLastName());
    }

    public void activeUser() {
        List<User> allUsers = adminService.findAllInactiveUsers();
        if (!allUsers.isEmpty()) {
            System.out.println("Active a single user");
            User user = (User) KeyboardUtility.askForElementInArray(allUsers.toArray());
            adminService.activeUser(user);
            System.out.printf("User %s %s with email %s is now active\n", user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            System.out.println("There are no single users to be activated");
        }

    }

    public void activeUsers() {
        displayInactiveUsers();
        String option = KeyboardUtility.askForString("Do you want to active all users? press (y/n) yes or no: ");
        if (option.toLowerCase().equals("y")) {
            adminService.activateAllUsers();
        } else {
            System.out.println("Any of the users were activated");
        }
    }

    public void startAdminRegistration() {
        System.out.println("***********************************************");
        System.out.println("Admin Registration, fill in the required fields");
        System.out.println("***********************************************");
        Admin newAdmin = registerNewAdmin();
        userAdminService.signupUser(newAdmin);
    }

    public Admin registerNewAdmin() {
        String firstName = MainAuthMenu.askForFirstName();
        String lastName = MainAuthMenu.askForLastName();
        String email = MainAuthMenu.askForEmail();
        String password = MainAuthMenu.askForPassword();
        Address address = MainAuthMenu.askForAddress();
        String telephone = MainAuthMenu.askForTelephone();
        Admin newUser = new Admin();
        newUser.setActive(true);
        newUser.setUserType(UserType.ADMIN);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAddress(address);
        newUser.setTelephone(telephone);
        return newUser;
    }

    public void startDisplaySubjects() {
        List<ClassSchoolSubject> classSchoolSubjectList = classSchoolSubjectService.findAll();
        classSchoolSubjectList.forEach(this::displaySubjectDetail);
    }

    public void displaySubjectDetail(ClassSchoolSubject subject) {
        System.out.printf("Subject: %s, Description: %s, start: %s, end: %s \n",
                subject.getName(),
                subject.getDescription(),
                subject.getStartDate().toString(),
                subject.getEndDate().toString());
    }

    public void selectClassesOption() {
        System.out.println("Assign student to a class or teacher to a class, select TEACHER or STUDENT");
        boolean continueSelection = false;
        while (!continueSelection) {
            Stream<UserTypeOption> filtered = Arrays.stream(UserTypeOption.values()).filter((value) -> !value.equals(UserType.ADMIN));
            UserTypeOption option = (UserTypeOption) KeyboardUtility.askForElementInArray(filtered.toArray());
            switch (option) {
                case STUDENT -> startAssignStudent(option);
                case TEACHER -> startAssignTeacher(option);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startAssignTeacher(UserTypeOption option) {
        System.out.println("You can search by filters or list, select continue to select by list");
        List<User> filteredUsers = selectFilterOption(option);
        if (filteredUsers != null && !filteredUsers.isEmpty()) {
            System.out.println("First, select the class you want to assign to the teacher");
            ClassSchoolSubject selectedClass = (ClassSchoolSubject) KeyboardUtility.askForElementInArray(classSchoolSubjectService.findAll().toArray());
            System.out.println("Select a teacher to be assigned to that class");
            Teacher selectedTeacher = (Teacher) KeyboardUtility.askForElementInArray(filteredUsers.toArray());
            teacherService.assignTeacherToClass(selectedTeacher, selectedClass);
        } else {
            System.out.println("No results with that name was found, let's search on the list");
            System.out.println("First, select the class you want to assign to the teacher");
            ClassSchoolSubject selectedClass = (ClassSchoolSubject) KeyboardUtility.askForElementInArray(classSchoolSubjectService.findAll().toArray());
            System.out.println("Select a teacher to be assigned to that class");
            Teacher selectedTeacher = (Teacher) KeyboardUtility.askForElementInArray(teacherService.findAllTeachers().toArray());
            teacherService.assignTeacherToClass(selectedTeacher, selectedClass);
        }
    }

    private void startAssignStudent(UserTypeOption option) {
        System.out.println("You can search by filters or list, select continue to select by list");
        List<User> filteredUsers = selectFilterOption(option);
        if (filteredUsers != null && !filteredUsers.isEmpty()) {
            System.out.println("List with result was found");
            System.out.println("First, select the class you want to assign to the student");
            ClassSchoolSubject selectedClass = (ClassSchoolSubject) KeyboardUtility.askForElementInArray(classSchoolSubjectService.findAll().toArray());
            System.out.println("Select a student to be assigned to that class");
            Student selectedStudent = (Student) KeyboardUtility.askForElementInArray(filteredUsers.toArray());
            studentService.assignStudentToClass(selectedClass.getId(), selectedStudent.getId());
        } else {
            System.out.println("No results with that name was found, let's search on the list");
            System.out.println("First, select the class you want to assign to the student");
            ClassSchoolSubject selectedClass = (ClassSchoolSubject) KeyboardUtility.askForElementInArray(classSchoolSubjectService.findAll().toArray());
            System.out.println("Select a student to be assigned to that class");
            Student selectedStudent = (Student) KeyboardUtility.askForElementInArray(studentService.fetchAllStudents().toArray());
            studentService.assignStudentToClass(selectedClass.getId(), selectedStudent.getId());
        }
    }

    private List<User> selectFilterOption(UserTypeOption userTypeOption) {
        boolean continueSelection = false;
        List<User> filteredUsers = new ArrayList<>(); // Initialize as an empty list

        while (!continueSelection) {
            FilterOption option = KeyboardUtility.askForElementInArray(FilterOption.values());
            switch (option) {
                case SEARCH_BY_NAME -> filteredUsers = startFilterByName(userTypeOption);
                case SEARCH_BY_EMAIL -> filteredUsers = startFilterByEmail(userTypeOption);
                case CONTINUE -> {
                    continueSelection = true;
                }
            }

            if (!filteredUsers.isEmpty()) {
                return filteredUsers; // Return the list if not empty
            } else if (option == CONTINUE) {
                break;
            } else {
                System.out.println("No results found. Please try again or continue with all users.");
            }
        }

        return filteredUsers;
    }


    private List<User> startFilterByEmail(UserTypeOption userTypeOption) {
        String input = KeyboardUtility.askForString(
                userTypeOption.equals(UserTypeOption.STUDENT) ? "Type student email: " : "Type teacher email: "
        );

        if (userTypeOption.equals(UserTypeOption.STUDENT)) {
            return studentService.findByEmailContainingIgnoreCase(input)
                    .stream().map(user -> (User) user)
                    .collect(Collectors.toList());
        } else {
            return teacherService.findByEmailContainingIgnoreCase(input)
                    .stream().map(user -> (User) user)
                    .collect(Collectors.toList());
        }
    }

    private List<User> startFilterByName(UserTypeOption userTypeOption) {
        String input = KeyboardUtility.askForString(
                userTypeOption.equals(UserTypeOption.STUDENT) ? "Type student name: " : "Type teacher name: "
        );

        if (userTypeOption.equals(UserTypeOption.STUDENT)) {
            return studentService.findByFirstNameContainingIgnoreCase(input)
                    .stream().map(user -> (User) user)
                    .collect(Collectors.toList());
        } else {
            return teacherService.findByFirstNameContainingIgnoreCase(input)
                    .stream().map(user -> (User) user)
                    .collect(Collectors.toList());
        }
    }

    public void startDisplayTeacherClasses() {
        List<Teacher> teachersDetailsInfo = teacherService.getTeachersWithClassDetails();
        teachersDetailsInfo.forEach(this::teacherClassDetail);
    }

    public void teacherClassDetail(Teacher teacher) {
        System.out.printf("Teacher: %s %s %n", teacher.getFirstName(), teacher.getLastName());

        for (ClassSchoolSubject subject : teacher.getSubjects()) {
            System.out.printf("Class Name: %s%n", subject.getName());
            System.out.printf("Start Date: %s%n", subject.getStartDate());
            System.out.printf("End Date: %s%n", subject.getEndDate());
            System.out.printf("Number of Enrolled Students: %d%n", subject.getStudentCount());
            System.out.println();
        }
    }

    public void displayAllBooks() {
        List<Book> teachersDetailsInfo = bookService.fetchAllBooks();
        teachersDetailsInfo.forEach(this::bookDetail);
    }

    public void bookDetail(Book book) {
        System.out.printf("Title: %s%n", book.getTitle());
        System.out.printf("Author: %s%n", book.getAuthor());
        System.out.printf("ISBN: %s%n", book.getIsbn());
        System.out.printf("Edition: %s%n", book.getEdition());
        System.out.println();
    }

    public String askForTitle() {
        return KeyboardUtility.askForString("Enter book title: ");
    }

    public String askForAuthor() {
        return KeyboardUtility.askForString("Enter book author: ");
    }

    public String askForIsbn() {
        return KeyboardUtility.askForString("Enter book ISBN: ");
    }

    public String askForEdition() {
        return KeyboardUtility.askForString("Enter book edition: ");
    }

    public Book createBook() {
        String title = askForTitle();
        String author = askForAuthor();
        String isbn = askForIsbn();
        String edition = askForEdition();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setEdition(edition);

        return book;
    }

    public void startBookCreation() {
        Book newBook = createBook();
        bookService.saveBook(newBook);
        System.out.printf("%s was saved to the database \n", newBook.getTitle());
    }

    void selectOperationOnBook() {
        System.out.println("Select operation to update book or delete it");
        boolean continueSelection = false;
        while (!continueSelection) {
            BookOperation option = (BookOperation) KeyboardUtility.askForElementInArray(BookOperation.values());
            switch (option) {
                case DELETE_BOOK -> startDeleteBook();
                case UPDATE_BOOK -> startUpdateBook();
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startUpdateBook() {
        System.out.println("If you don't desire to update simply press enter in the value");
        Book selectedBook = (Book) KeyboardUtility.askForElementInArray(bookService.fetchAllBooks().toArray());
        String title = askForTitle();
        String author = askForAuthor();
        String isbn = askForIsbn();
        String edition = askForEdition();

        if (!title.trim().isEmpty()) {
            selectedBook.setTitle(title);
        }
        if (!author.trim().isEmpty()) {
            selectedBook.setAuthor(author);
        }
        if (!isbn.trim().isEmpty()) {
            selectedBook.setIsbn(isbn);
        }
        if (!edition.trim().isEmpty()) {
            selectedBook.setEdition(edition);
        }
        bookService.updateBookById(selectedBook);
    }

    private void startDeleteBook() {
        List<Book> books = bookService.fetchAllBooks();
        if (!books.isEmpty()) {
            System.out.println("Select book to be deleted");
            Book selectedBook = (Book) KeyboardUtility.askForElementInArray(bookService.fetchAllBooks().toArray());
            bookService.deleteById(selectedBook.getId());
        } else {
            System.out.println("No book was found");
        }
    }

    private void searchBook() {
        System.out.println("Search book by title or author");
        boolean continueSelection = false;
        while (!continueSelection) {
            BookFilterOption option = (BookFilterOption) KeyboardUtility.askForElementInArray(BookFilterOption.values());
            switch (option) {
                case SEARCH_BY_AUTHOR -> startSearchByAuthor();
                case SEARCH_BY_TITLE -> startSearchByTitle();
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startSearchByTitle() {
        System.out.println("Search book by title");
        String searchTerm = askForTitle();
        List<Book> books = bookService.findByTitleContainingIgnoreCase(searchTerm);
        if (books.isEmpty()) {
            System.out.println("No book was found");
        } else {
            books.forEach(this::bookDetail);
        }
    }

    private void startSearchByAuthor() {
        System.out.println("Search book by author");
        String searchTerm = askForAuthor();
        List<Book> books = bookService.findByAuthorContainingIgnoreCase(searchTerm);
        if (books.isEmpty()) {
            System.out.println("No book was found");
        } else {
            books.forEach(this::bookDetail);
        }
    }
}
