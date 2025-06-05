package com.mvbr.jwtspringsecurity.application.port;

public interface LoginAttemptPort {
    void recordLoginAttempt(String username, boolean success);
    boolean isAccountLocked(String username);
    void unlockAccount(String username);
}
