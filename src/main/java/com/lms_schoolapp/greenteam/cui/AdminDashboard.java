package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.AdminService;
import com.lms_schoolapp.greenteam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminDashboard {
    private final AdminService adminService;
    public void printWelcomeMessage() {
        System.out.println("***************");
        System.out.println("Admin Dashboard");
        System.out.println("***************");
    }

    public void printOptionAdminDashboard(Admin loggedInAdmin) {
        System.out.printf("Welcome back %s %s", loggedInAdmin.getFirstName(), loggedInAdmin.getLastName());
        boolean continueSelection = false;
        while (!continueSelection) {
            AdminMenuOption option = KeyboardUtility.askForElementInArray(AdminMenuOption.values());
            switch (option) {
                case ACTIVE_USER -> activeUser();
                case REGISTER_ADMIN -> registerNewAdmin();
                case ACTIVE_USERS -> activeUsers();
                case DISPLAY_INACTIVE_USERS -> displayInactiveUsers();
                case EXIT -> {
                    loggedInAdmin = null;
                    continueSelection = true;
                }
            }
        }
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

    public void registerNewAdmin() {
    }
}
