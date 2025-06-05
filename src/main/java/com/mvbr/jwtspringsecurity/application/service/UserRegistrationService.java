package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.*;

public class UserRegistrationService {
    private final UserRegistrationPort userRegistrationPort;
    private final EmailConfirmationPort emailConfirmationPort;
    private final PasswordPolicyPort passwordPolicyPort;

    public UserRegistrationService(UserRegistrationPort userRegistrationPort,
                                   EmailConfirmationPort emailConfirmationPort,
                                   PasswordPolicyPort passwordPolicyPort) {
        this.userRegistrationPort = userRegistrationPort;
        this.emailConfirmationPort = emailConfirmationPort;
        this.passwordPolicyPort = passwordPolicyPort;
    }

    public void registerUser(String username, String email, String password) {
        if (!userRegistrationPort.isUsernameAvailable(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!userRegistrationPort.isEmailAvailable(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!passwordPolicyPort.isPasswordStrong(password)) {
            throw new IllegalArgumentException(passwordPolicyPort.getPasswordPolicyDescription());
        }
        userRegistrationPort.registerUser(username, email, password);
        String token = java.util.UUID.randomUUID().toString();
        emailConfirmationPort.sendConfirmationEmail(email, token);
    }
}
