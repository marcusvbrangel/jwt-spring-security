package com.mvbr.jwtspringsecurity.application.port;

public interface ConsentPort {
    void recordConsent(String username, String consentType, boolean accepted);
    boolean hasConsented(String username, String consentType);
}
