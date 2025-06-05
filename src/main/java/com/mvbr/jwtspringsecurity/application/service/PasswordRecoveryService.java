package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.PasswordRecoveryPort;

public class PasswordRecoveryService {
    private final PasswordRecoveryPort passwordRecoveryPort;

    public PasswordRecoveryService(PasswordRecoveryPort passwordRecoveryPort) {
        this.passwordRecoveryPort = passwordRecoveryPort;
    }

    public void initiateRecovery(String email) {
        String token = java.util.UUID.randomUUID().toString();
        passwordRecoveryPort.sendRecoveryToken(email, token);
    }

    public void resetPassword(String token, String newPassword) {
        if (!passwordRecoveryPort.validateRecoveryToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        // Aqui você pode obter o e-mail associado ao token, se necessário
        // Exemplo: String email = passwordRecoveryPort.getEmailByToken(token);
        // passwordRecoveryPort.updatePassword(email, newPassword);
        // Para simplificação, supondo que o token já está vinculado ao e-mail
        passwordRecoveryPort.updatePassword(null, newPassword);
    }
}
