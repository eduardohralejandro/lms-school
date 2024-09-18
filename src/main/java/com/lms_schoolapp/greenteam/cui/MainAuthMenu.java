package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MainAuthMenu {
    private final UserService<User> userService;
    private final UserService<Student> userStudentService;
    private final UserService<Teacher> userTeacherService;
    private final UserService<Admin> userAdminService;
    private final AdminDashboard adminDashboard;
    private final StudentDashboard studentDashboard;
    private final TeacherDashboard teacherDashboard;

    public void start() {
        welcomeMessage();
        printOptionMenu();
    }

    public void printOptionMenu() {
        mainMenuMessageHeader();
        boolean continueSelection = false;
        while (!continueSelection) {
            MainMenuOption option = KeyboardUtility.askForElementInArray(MainMenuOption.values());
            switch (option) {
                case REGISTER -> registerUser();
                case LOGIN -> loginUser();
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    public void loginUser() {
        User loggedInUser = startLoginUser();
        if (loggedInUser.getUserType().equals(UserType.STUDENT)) {
            studentDashboard.start(loggedInUser);
        } else if (loggedInUser.getUserType().equals(UserType.TEACHER)) {
            teacherDashboard.start(loggedInUser);
        } else if (loggedInUser.getUserType().equals(UserType.ADMIN)) {
            adminDashboard.start(loggedInUser);
        }
    }

    public void registerUser() {
        System.out.println("Registering user");
        User newUser = createUser();
        if (newUser.getUserType().equals(UserType.STUDENT)) {
            userStudentService.signupUser((Student) newUser);
        }
        if (newUser.getUserType().equals(UserType.TEACHER)) {
            userTeacherService.signupUser((Teacher) newUser);
        }

        if (newUser.getUserType().equals(UserType.ADMIN)) {
            userAdminService.signupUser((Admin) newUser);
        }
        System.out.println("You can now login with your credentials. Ask administrator to active your account");
    }

    public static void welcomeMessage() {
        System.out.println("************************************");
        System.out.println("Welcome to School Management System");
        System.out.println("************************************");
    }

    public static void mainMenuMessageHeader() {
        System.out.println("**********");
        System.out.println("Main menu");
        System.out.println("**********");
    }


    public static String askForFirstName() {
        return KeyboardUtility.askForString("Enter name: ");
    }

    public static String askForLastName() {
        return KeyboardUtility.askForString("Enter last name: ");
    }

    public static String askForEmail() {
        return KeyboardUtility.askForString("Enter email: ");
    }

    public static String askForPassword() {
        return KeyboardUtility.askForString("Enter password: ");
    }

    public static Address askForAddress() {
        System.out.println("Your address");
        String street = KeyboardUtility.askForString("Enter street: ");
        String city = KeyboardUtility.askForString("Enter city: ");
        String state = KeyboardUtility.askForString("Enter state: ");
        return new Address(street, city, state);
    }

    public static String askForTelephone() {
        return KeyboardUtility.askForString("Enter telephone: ");
    }

    public static UserType askForUsertype() {
        System.out.println("Are you a Student or a Teacher: ");
        UserType[] filteredValues = Arrays.stream(UserType.values())
                .filter(type -> type != UserType.ADMIN)
                .toArray(UserType[]::new);
        return KeyboardUtility.askForElementInArray(filteredValues);
    }


    public User createUser() {
        String firstName = askForFirstName();
        String lastName = askForLastName();
        String email = askForEmail();
        String password = askForPassword();
        Address address = askForAddress();
        String telephone = askForTelephone();
        UserType userType = askForUsertype();
        User newUser;
        if (userType == UserType.STUDENT) {
            newUser = new Student();
            newUser.setUserType(UserType.STUDENT);
        } else if (userType == UserType.TEACHER) {
            newUser = new Teacher();
            newUser.setUserType(UserType.TEACHER);
        } else {
            throw new IllegalArgumentException("Unsupported UserType");
        }
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAddress(address);
        newUser.setTelephone(telephone);
        return newUser;
    }

    public User startLoginUser() {
        System.out.println("Provide credentials to login");
        String email = askForEmail();
        String password = askForPassword();
        return userService.loginUser(email, password);
    }
}
