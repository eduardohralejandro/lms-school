package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.model.Address;
import com.lms_schoolapp.greenteam.model.Admin;
import com.lms_schoolapp.greenteam.model.Student;
import com.lms_schoolapp.greenteam.model.UserType;
import com.lms_schoolapp.greenteam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CuiApp implements CommandLineRunner {
    private final UserService<Admin> userService;
    private final UserService<Student> studentService;


    @Override
    public void run(String... args) throws Exception {
    }

    private void addUser() {

    }

    private void login() {
    }
}
