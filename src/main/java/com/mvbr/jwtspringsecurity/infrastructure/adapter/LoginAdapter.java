package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.LoginPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAdapter implements LoginPort {
    private final Map<String, String> users = new ConcurrentHashMap<>();
    private final Map<String, Boolean> loggedIn = new ConcurrentHashMap<>();

    public LoginAdapter() {
        // Usuário de exemplo
        users.put("user", "password");
    }

    @Override
    public boolean login(String username, String password) {
        boolean authenticated = users.containsKey(username) && users.get(username).equals(password);
        if (authenticated) {
            loggedIn.put(username, true);
        }
        return authenticated;
    }

    @Override
    public void logout(String username) {
        loggedIn.put(username, false);
        System.out.println("Logout realizado para: " + username);
    }

    public boolean isLoggedIn(String username) {
        return loggedIn.getOrDefault(username, false);
    }
}

