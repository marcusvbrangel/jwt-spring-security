package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.UserRegistrationPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRegistrationAdapter implements UserRegistrationPort {
    private final Map<String, String> users = new ConcurrentHashMap<>();
    private final Map<String, String> emails = new ConcurrentHashMap<>();

    @Override
    public void registerUser(String username, String email, String password) {
        users.put(username, password);
        emails.put(email, username);
        System.out.println("Usuário registrado: " + username + " (" + email + ")");
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !users.containsKey(username);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !emails.containsKey(email);
    }
}

