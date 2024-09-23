package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.user.model.User;
import com.lms_schoolapp.greenteam.user.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public void activateAllUsers() {
        adminRepository.updateAllUsersByActive(true);
    }

    @Override
    public void activeUser(User user) {
        adminRepository.updateById(user.getId(), true);
    }

    @Override
    public List<User> findAllInactiveUsers() {
        return adminRepository.findByActiveFalse();
    }
}
