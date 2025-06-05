package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.LoginAttemptPort;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAttemptAdapter implements LoginAttemptPort {
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME_MILLIS = 10 * 60 * 1000; // 10 minutos

    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private final Map<String, Long> lockTimestamps = new ConcurrentHashMap<>();

    @Override
    public void recordLoginAttempt(String username, boolean success) {
        if (success) {
            attempts.remove(username);
            lockTimestamps.remove(username);
        } else {
            int count = attempts.getOrDefault(username, 0) + 1;
            attempts.put(username, count);
            if (count >= MAX_ATTEMPTS) {
                lockTimestamps.put(username, System.currentTimeMillis());
            }
        }
    }

    @Override
    public boolean isAccountLocked(String username) {
        Long lockTime = lockTimestamps.get(username);
        if (lockTime == null) return false;
        if (System.currentTimeMillis() - lockTime > LOCK_TIME_MILLIS) {
            unlockAccount(username);
            return false;
        }
        return true;
    }

    @Override
    public void unlockAccount(String username) {
        attempts.remove(username);
        lockTimestamps.remove(username);
    }
}

