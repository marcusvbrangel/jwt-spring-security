package com.mvbr.jwtspringsecurity.application.port;

public interface LoginPort {
    boolean login(String username, String password);
    void logout(String username);
}
