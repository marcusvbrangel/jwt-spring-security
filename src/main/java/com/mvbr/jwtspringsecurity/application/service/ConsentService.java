package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.ConsentPort;

public class ConsentService {
    private final ConsentPort consentPort;

    public ConsentService(ConsentPort consentPort) {
        this.consentPort = consentPort;
    }

    public void acceptConsent(String username, String consentType) {
        consentPort.recordConsent(username, consentType, true);
    }

    public void rejectConsent(String username, String consentType) {
        consentPort.recordConsent(username, consentType, false);
    }

    public boolean hasConsented(String username, String consentType) {
        return consentPort.hasConsented(username, consentType);
    }
}

