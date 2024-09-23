package com.lms_schoolapp.greenteam.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse<T> {
    private boolean success;
    private String message;
    private T user;

    public LoginResponse(boolean success, String message, T user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }
}
