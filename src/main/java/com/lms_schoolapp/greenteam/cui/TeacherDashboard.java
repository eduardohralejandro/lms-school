package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherDashboard {
    public void start(User loggedInUser) {
        printWelcomeMessage();
        System.out.printf("Welcome back: %s %s \n", loggedInUser.getFirstName(), loggedInUser.getLastName());
    }

    public void printWelcomeMessage() {
        System.out.println("*****************");
        System.out.println("Teacher Dashboard");
        System.out.println("*****************");
    }
}
