package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.LoginAttemptPort;

public class LoginAttemptService {
    private final LoginAttemptPort loginAttemptPort;

    public LoginAttemptService(LoginAttemptPort loginAttemptPort) {
        this.loginAttemptPort = loginAttemptPort;
    }

    public void recordLoginAttempt(String username, boolean success) {
        loginAttemptPort.recordLoginAttempt(username, success);
    }

    public boolean isAccountLocked(String username) {
        return loginAttemptPort.isAccountLocked(username);
    }

    public void unlockAccount(String username) {
        loginAttemptPort.unlockAccount(username);
    }
}

