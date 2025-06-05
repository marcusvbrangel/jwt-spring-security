package com.mvbr.jwtspringsecurity.application.port;

public interface AuditPort {
    void logEvent(String username, String action, String details);
}
