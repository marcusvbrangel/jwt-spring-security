package com.mvbr.jwtspringsecurity.application.port;

public interface TwoFactorAuthPort {
    void send2FACode(String username, String destination);
    boolean verify2FACode(String username, String code);
    boolean is2FAEnabled(String username);
    void enable2FA(String username);
    void disable2FA(String username);
}
