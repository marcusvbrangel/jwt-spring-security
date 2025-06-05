package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.PasswordPolicyPort;
import org.springframework.stereotype.Component;

@Component
public class PasswordPolicyAdapter implements PasswordPolicyPort {
    @Override
    public boolean isPasswordStrong(String password) {
        // Exemplo simples: pelo menos 8 caracteres, 1 maiúscula, 1 minúscula, 1 número
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    @Override
    public String getPasswordPolicyDescription() {
        return "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula e um número.";
    }
}

