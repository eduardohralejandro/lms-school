package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TeacherDashboard {
    private final ClassSchoolSubjectService classSchoolSubjectService;
    private final BookService bookService;
    private final StudentService studentService;
    private final ForumService forumService;
    private final ThreadService threadService;
    private final PostService postService;
    private final WebshopMainConsole webshopMainConsole;

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
                case DISPLAY_STUDENTS_SUBSCRIBED_TO_MY_CLASS -> startStudentClass(loggedInUser);
                case DISPLAY_FORUMS_CREATE_THREAD -> displayForumTeacher(loggedInUser);
                case DISPLAY_THREADS -> displayThreads(loggedInUser);
                case VISIT_WEB_SHOP -> webshopMainConsole.selectOptionWebshop(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void displayForumTeacher(User loggedInUser) {
        System.out.println("Overview of teacher forums, select one to start thread");
        List<Forum> studentForums = forumService.findForumByDtype(ForumType.TEACHER);
        Forum forum = (Forum) KeyboardUtility.askForElementInArray(studentForums.toArray());
        startCreateThreadInForum(forum, loggedInUser);
    }

    private void startCreateThreadInForum(Forum forum, User loggedInUser) {
        System.out.println("You can start creating a thread");
        String threadTitle = askForThreadTitle();
        threadService.createThread(loggedInUser.getId(), forum.getId(), threadTitle);
        System.out.printf("Thread with title: %s was created\n ", threadTitle);
        displayThreads(loggedInUser);
    }

    public String askForThreadTitle() {
        return KeyboardUtility.askForString("Enter thread title: ");
    }

    public void displayThreads(User loggedInUser) {
        List<ForumType> forumTypes = Arrays.asList(ForumType.GENERAL, ForumType.TEACHER);
        List<ThreadRoom> listOfThreads = threadService.findAllByForumForumTypeInOrderByCreatedDateAsc(forumTypes);
        ThreadRoom threadRoomSelected = (ThreadRoom) KeyboardUtility.askForElementInArray(listOfThreads.toArray());
        selectActionInThreadForPosting(threadRoomSelected, loggedInUser);
    }

    private void selectActionInThreadForPosting(ThreadRoom threadRoomSelected, User loggedInUser) {
        System.out.println("Select operation for posts");
        boolean continueSelection = false;
        while (!continueSelection) {
            PostAction option = (PostAction) KeyboardUtility.askForElementInArray(PostAction.values());
            switch (option) {
                case CREATE_POST_IN_THREAD -> createPostInThread(threadRoomSelected, loggedInUser);
                case VIEW_POSTS_IN_THREAD -> viewPostsInThread(threadRoomSelected, loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void createPostInThread(ThreadRoom threadRoomSelected, User loggedInUser) {
        System.out.println("Create post");
        String postTitle = askForPostTitle();
        Post post = new Post();
        post.setBody(postTitle);
        post.setCreatedAt(LocalDateTime.now());
        postService.createPost(post, loggedInUser.getId(), threadRoomSelected.getId());
        System.out.printf("%s was created\n", postTitle);
    }

    public String askForPostTitle() {
        return KeyboardUtility.askForString("Enter post title: ");
    }

    private void viewPostsInThread(ThreadRoom threadRoomSelected, User loggedInUser) {
        List<Post> postsOfThread = postService.findAllPostsByThreadId(threadRoomSelected.getId());
        postsOfThread.forEach(this::postDetail);
    }

    public void postDetail(Post post) {
        User user = post.getUser();
        LocalDateTime createdAt = post.getCreatedAt();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, MMMM dd, yyyy");
        String formattedDate = createdAt.format(formatter);

        System.out.printf("Post Title: %s\nUser: %s %s\nCreated At: %s\n",
                post.getBody(),
                user.getFirstName(),
                user.getLastName(),
                formattedDate);
        System.out.println();
        System.out.println();
    }

    private void startStudentClass(User loggedInUser) {
        List<ClassSchoolSubject> classSchoolSubjectList = classSchoolSubjectService.findClassesWithTeacher(loggedInUser.getId());

        classSchoolSubjectList.forEach(classSchoolSubject -> {
            List<Student> students = studentService.findStudentsByClassAndTeacher(classSchoolSubject.getId(), loggedInUser.getId());

            System.out.println("Class: " + classSchoolSubject.getName() + " - Subject: " + classSchoolSubject.getDescription());
            System.out.println("Total Students Subscribed: " + students.size());  // Print total number of students

            students.forEach(student -> {
                System.out.println("Student Name: " + student.getFirstName() + " " + student.getLastName() + ", Email: " + student.getEmail());
            });

            System.out.println();
        });
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
