package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.EmailConfirmationPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmailConfirmationAdapter implements EmailConfirmationPort {
    private final Map<String, String> emailToToken = new ConcurrentHashMap<>();
    private final Map<String, Boolean> emailConfirmed = new ConcurrentHashMap<>();

    @Override
    public void sendConfirmationEmail(String email, String token) {
        emailToToken.put(email, token);
        emailConfirmed.put(email, false);
        System.out.println("Enviando e-mail de confirmação para: " + email + " com token: " + token);
    }

    @Override
    public boolean confirmEmail(String token) {
        for (Map.Entry<String, String> entry : emailToToken.entrySet()) {
            if (entry.getValue().equals(token)) {
                emailConfirmed.put(entry.getKey(), true);
                System.out.println("E-mail confirmado: " + entry.getKey());
                return true;
            }
        }
        return false;
    }

    public boolean isEmailConfirmed(String email) {
        return emailConfirmed.getOrDefault(email, false);
    }
}

