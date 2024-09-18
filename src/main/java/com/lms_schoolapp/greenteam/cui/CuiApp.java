package com.lms_schoolapp.greenteam.cui;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CuiApp implements CommandLineRunner {
    private final MainAuthMenu mainAuthMenu;
    private final AdminDashboard adminDashboard;

    @Override
    public void run(String... args) throws Exception {
        adminDashboard.displayInactiveUsers();
        adminDashboard.activeUser();
        adminDashboard.activeUsers();
        mainAuthMenu.start();
    }

    private void addUser() {

    }

    private void login() {
    }
}
