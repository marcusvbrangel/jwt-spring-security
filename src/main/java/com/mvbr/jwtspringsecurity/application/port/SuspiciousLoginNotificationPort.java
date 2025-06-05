package com.mvbr.jwtspringsecurity.application.port;

public interface SuspiciousLoginNotificationPort {
    void notifySuspiciousLogin(String username, String details);
}
