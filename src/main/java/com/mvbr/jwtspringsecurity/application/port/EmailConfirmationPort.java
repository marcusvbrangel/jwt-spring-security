package com.mvbr.jwtspringsecurity.application.port;

public interface EmailConfirmationPort {
    void sendConfirmationEmail(String email, String token);
    boolean confirmEmail(String token);
}
