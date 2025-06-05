package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.PasswordRecoveryPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PasswordRecoveryAdapter implements PasswordRecoveryPort {
    private final Map<String, String> emailToToken = new ConcurrentHashMap<>();
    private final Map<String, String> emailToPassword = new ConcurrentHashMap<>();

    @Override
    public void sendRecoveryToken(String email, String token) {
        emailToToken.put(email, token);
        System.out.println("Enviando token de recuperação para: " + email + " token: " + token);
    }

    @Override
    public boolean validateRecoveryToken(String token) {
        return emailToToken.containsValue(token);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        emailToPassword.put(email, newPassword);
        System.out.println("Senha atualizada para: " + email);
    }
}

