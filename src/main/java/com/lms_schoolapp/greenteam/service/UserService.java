package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService<T extends User> {
    T loginUser(String email, String password);

    void signupUser(T user);

    T updateUser(T user);

    void deleteUser(T user);

    T fetchUserByEmail(String email);
    List<T> fetchAllUsers();
}
