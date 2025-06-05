package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.PasswordPolicyPort;

public class PasswordPolicyService {
    private final PasswordPolicyPort passwordPolicyPort;

    public PasswordPolicyService(PasswordPolicyPort passwordPolicyPort) {
        this.passwordPolicyPort = passwordPolicyPort;
    }

    public boolean isPasswordStrong(String password) {
        return passwordPolicyPort.isPasswordStrong(password);
    }

    public String getPasswordPolicyDescription() {
        return passwordPolicyPort.getPasswordPolicyDescription();
    }
}

