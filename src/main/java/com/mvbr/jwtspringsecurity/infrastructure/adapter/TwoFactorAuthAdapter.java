package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.TwoFactorAuthPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TwoFactorAuthAdapter implements TwoFactorAuthPort {
    private final Map<String, String> userToCode = new ConcurrentHashMap<>();
    private final Map<String, Boolean> user2FAEnabled = new ConcurrentHashMap<>();

    @Override
    public void send2FACode(String username, String destination) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000); // 6 dígitos
        userToCode.put(username, code);
        System.out.println("Enviando código 2FA para " + destination + ": " + code);
    }

    @Override
    public boolean verify2FACode(String username, String code) {
        return code.equals(userToCode.get(username));
    }

    @Override
    public boolean is2FAEnabled(String username) {
        return user2FAEnabled.getOrDefault(username, false);
    }

    @Override
    public void enable2FA(String username) {
        user2FAEnabled.put(username, true);
    }

    @Override
    public void disable2FA(String username) {
        user2FAEnabled.put(username, false);
    }
}

