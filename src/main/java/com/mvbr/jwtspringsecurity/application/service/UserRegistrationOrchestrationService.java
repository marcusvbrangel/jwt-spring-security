package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.UserRegistrationPort;
import com.mvbr.jwtspringsecurity.application.port.PasswordPolicyPort;
import com.mvbr.jwtspringsecurity.application.port.EmailConfirmationPort;

public class UserRegistrationOrchestrationService {
    private final UserRegistrationPort userRegistrationPort;
    private final PasswordPolicyPort passwordPolicyPort;
    private final EmailConfirmationPort emailConfirmationPort;

    public UserRegistrationOrchestrationService(UserRegistrationPort userRegistrationPort,
                                               PasswordPolicyPort passwordPolicyPort,
                                               EmailConfirmationPort emailConfirmationPort) {
        this.userRegistrationPort = userRegistrationPort;
        this.passwordPolicyPort = passwordPolicyPort;
        this.emailConfirmationPort = emailConfirmationPort;
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

    public boolean isUsernameAvailable(String username) {
        return userRegistrationPort.isUsernameAvailable(username);
    }

    public boolean isEmailAvailable(String email) {
        return userRegistrationPort.isEmailAvailable(email);
    }
}

