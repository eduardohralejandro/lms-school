package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CuiApp implements CommandLineRunner {
    private final MainMenu mainMenu;

    @Override
    public void run(String... args) throws Exception {
        mainMenu.start();
    }

    private void addUser() {

    }

    private void login() {
    }
}
