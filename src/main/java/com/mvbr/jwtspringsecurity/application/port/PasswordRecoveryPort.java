package com.mvbr.jwtspringsecurity.application.port;

public interface PasswordRecoveryPort {
    void sendRecoveryToken(String email, String token);
    boolean validateRecoveryToken(String token);
    void updatePassword(String email, String newPassword);
}
