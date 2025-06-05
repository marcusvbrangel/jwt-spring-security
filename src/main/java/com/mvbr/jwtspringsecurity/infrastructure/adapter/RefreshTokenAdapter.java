package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.RefreshTokenPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RefreshTokenAdapter implements RefreshTokenPort {
    private final Map<String, String> userToToken = new ConcurrentHashMap<>();
    private final Map<String, Boolean> revokedTokens = new ConcurrentHashMap<>();

    @Override
    public String createRefreshToken(String username) {
        String token = java.util.UUID.randomUUID().toString();
        userToToken.put(username, token);
        revokedTokens.put(token, false);
        return token;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return userToToken.containsValue(token) && !revokedTokens.getOrDefault(token, false);
    }

    @Override
    public void revokeRefreshToken(String token) {
        revokedTokens.put(token, true);
    }
}

