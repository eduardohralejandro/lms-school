package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.book.model.Book;
import com.lms_schoolapp.greenteam.book.service.BookService;
import com.lms_schoolapp.greenteam.classroom.model.ClassSchoolSubject;
import com.lms_schoolapp.greenteam.classroom.service.ClassSchoolSubjectService;
import com.lms_schoolapp.greenteam.common.enums.FilterOption;
import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.social.model.*;
import com.lms_schoolapp.greenteam.social.service.ForumService;
import com.lms_schoolapp.greenteam.social.service.PostService;
import com.lms_schoolapp.greenteam.social.service.ThreadService;
import com.lms_schoolapp.greenteam.user.model.Student;
import com.lms_schoolapp.greenteam.user.model.StudentMainMenuOption;
import com.lms_schoolapp.greenteam.user.model.Teacher;
import com.lms_schoolapp.greenteam.user.model.User;
import com.lms_schoolapp.greenteam.user.service.StudentService;
import com.lms_schoolapp.greenteam.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentDashboard {
    private final ClassSchoolSubjectService classSchoolSubjectService;
    private final BookService bookService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ForumService forumService;
    private final ThreadService threadService;
    private final PostService postService;
    private final WebshopMainConsole webshopMainConsole;

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
                case CHAT_WITH_STUDENTS -> startChatWithStudent(loggedInUser);
                case DISPLAY_FORUMS_CREATE_THREAD -> displayForumStudent(loggedInUser);
                case DISPLAY_THREADS -> displayThreads(loggedInUser);
                case VISIT_WEB_SHOP -> webshopMainConsole.selectOptionWebshop(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void displayForumStudent(User loggedInUser) {
        System.out.println("Overview of student forums, select one to start thread");
        List<Forum> studentForums = forumService.findForumByDtype(ForumType.STUDENT);
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

    public void displayThreads(User loggedInUser) {
        List<ForumType> forumTypes = Arrays.asList(ForumType.GENERAL, ForumType.STUDENT);
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

    public String askForThreadTitle() {
        return KeyboardUtility.askForString("Enter thread title: ");
    }

    private void startChatWithStudent(User loggedInUser) {
        System.out.println("Search student by name, email or continue to list");
        boolean continueSelection = false;
        while (!continueSelection) {
            FilterOption option = (FilterOption) KeyboardUtility.askForElementInArray(FilterOption.values());
            switch (option) {
                case SEARCH_BY_EMAIL -> startSearchByEmailToChat();
                case SEARCH_BY_NAME -> startSearchByNameToChat();
                case CONTINUE -> {
                    startDisplayAllStudents();
                    continueSelection = true;
                }
            }
        }
    }

    private void startDisplayAllStudents() {
        List<Student> allStudents = studentService.fetchAllStudents();
        if (allStudents.isEmpty()) {
            System.out.println("No students found");
        } else {
            Student selectedStudent = (Student) KeyboardUtility.askForElementInArray(allStudents.toArray());
        }
    }

    private void startSearchByNameToChat() {
        String studentName = KeyboardUtility.askForString("Enter student name: ");
        List<Student> studentsFilteredByName = studentService.findByFirstNameContainingIgnoreCase(studentName);
        if (studentsFilteredByName != null) {
            Student foundStudent = (Student) KeyboardUtility.askForElementInArray(studentsFilteredByName.toArray());
        } else {
            System.out.println("Students not found by name");
            System.out.println("Press continue to check by list of students in the database");
        }
    }

    private void startSearchByEmailToChat() {
        String studentEmail = KeyboardUtility.askForString("Enter student email: ");
        List<Student> studentsFilteredByName = studentService.findByEmailContainingIgnoreCase(studentEmail);
        if (studentsFilteredByName != null) {
            Student foundStudent = (Student) KeyboardUtility.askForElementInArray(studentsFilteredByName.toArray());
        } else {
            System.out.println("Students not found by name");
            System.out.println("Press continue to check by list of students in the database");
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
