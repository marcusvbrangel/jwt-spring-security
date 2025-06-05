package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.EmailConfirmationPort;

public class EmailConfirmationService {
    private final EmailConfirmationPort emailConfirmationPort;

    public EmailConfirmationService(EmailConfirmationPort emailConfirmationPort) {
        this.emailConfirmationPort = emailConfirmationPort;
    }

    public void sendConfirmationEmail(String email, String token) {
        emailConfirmationPort.sendConfirmationEmail(email, token);
    }

    public boolean confirmEmail(String token) {
        return emailConfirmationPort.confirmEmail(token);
    }
}

