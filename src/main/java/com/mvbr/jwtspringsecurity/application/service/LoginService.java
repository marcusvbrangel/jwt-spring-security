package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.LoginPort;

public class LoginService {
    private final LoginPort loginPort;

    public LoginService(LoginPort loginPort) {
        this.loginPort = loginPort;
    }

    public boolean login(String username, String password) {
        return loginPort.login(username, password);
    }

    public void logout(String username) {
        loginPort.logout(username);
    }
}

