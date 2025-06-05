package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.ConsentPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConsentAdapter implements ConsentPort {
    private final Map<String, Map<String, Boolean>> userConsents = new ConcurrentHashMap<>();

    @Override
    public void recordConsent(String username, String consentType, boolean accepted) {
        userConsents.computeIfAbsent(username, k -> new ConcurrentHashMap<>()).put(consentType, accepted);
    }

    @Override
    public boolean hasConsented(String username, String consentType) {
        return userConsents.getOrDefault(username, new ConcurrentHashMap<>()).getOrDefault(consentType, false);
    }
}

