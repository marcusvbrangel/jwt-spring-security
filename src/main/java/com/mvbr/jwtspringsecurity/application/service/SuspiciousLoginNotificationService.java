package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.SuspiciousLoginNotificationPort;

public class SuspiciousLoginNotificationService {
    private final SuspiciousLoginNotificationPort notificationPort;

    public SuspiciousLoginNotificationService(SuspiciousLoginNotificationPort notificationPort) {
        this.notificationPort = notificationPort;
    }

    public void notify(String username, String details) {
        notificationPort.notifySuspiciousLogin(username, details);
    }
}

