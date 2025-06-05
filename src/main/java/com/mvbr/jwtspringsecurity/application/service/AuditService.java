package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.AuditPort;

public class AuditService {
    private final AuditPort auditPort;

    public AuditService(AuditPort auditPort) {
        this.auditPort = auditPort;
    }

    public void logEvent(String username, String action, String details) {
        auditPort.logEvent(username, action, details);
    }
}

