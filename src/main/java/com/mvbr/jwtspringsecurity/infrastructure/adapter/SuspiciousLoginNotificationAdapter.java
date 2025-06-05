package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.SuspiciousLoginNotificationPort;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousLoginNotificationAdapter implements SuspiciousLoginNotificationPort {
    @Override
    public void notifySuspiciousLogin(String username, String details) {
        // Aqui você pode enviar e-mail, SMS, push notification, etc.
        System.out.printf("[ALERTA] Login suspeito detectado para %s: %s\n", username, details);
    }
}

