package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.AdminService;
import com.lms_schoolapp.greenteam.service.ClassSchoolSubjectService;
import com.lms_schoolapp.greenteam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminDashboard {
    private final AdminService adminService;
    private final UserService<Admin> userAdminService;
    private final ClassSchoolSubjectService classSchoolSubjectService;

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
                case EXIT -> {
                    loggedInAdmin = null;
                    continueSelection = true;
                }
            }
        }
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
        System.out.println(allUsers.size());
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
}
