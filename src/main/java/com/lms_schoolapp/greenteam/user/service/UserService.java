package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.common.response.LoginResponse;
import com.lms_schoolapp.greenteam.user.model.User;

import java.util.List;

public interface UserService<T extends User> {
    LoginResponse<T> loginUser(String email, String password);

    void signupUser(T user);

    T updateUser(T user);

    void deleteUser(T user);

    T fetchUserByEmail(String email);
    List<T> fetchAllUsers();
}
