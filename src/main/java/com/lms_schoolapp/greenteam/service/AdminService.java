package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.User;

import java.util.List;

public interface AdminService {
    void activateAllUsers();

    void activeUser(User user);

    List<User> findAllInactiveUsers();
}
