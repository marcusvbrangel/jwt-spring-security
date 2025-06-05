package com.mvbr.jwtspringsecurity.application.port;

public interface UserRegistrationPort {
    void registerUser(String username, String email, String password);
    boolean isUsernameAvailable(String username);
    boolean isEmailAvailable(String email);
}
