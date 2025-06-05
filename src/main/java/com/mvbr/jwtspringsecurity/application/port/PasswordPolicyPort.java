package com.mvbr.jwtspringsecurity.application.port;

public interface PasswordPolicyPort {
    boolean isPasswordStrong(String password);
    String getPasswordPolicyDescription();
}
