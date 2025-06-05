package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.TwoFactorAuthPort;

public class TwoFactorAuthService {
    private final TwoFactorAuthPort twoFactorAuthPort;

    public TwoFactorAuthService(TwoFactorAuthPort twoFactorAuthPort) {
        this.twoFactorAuthPort = twoFactorAuthPort;
    }

    public void send2FACode(String username, String destination) {
        twoFactorAuthPort.send2FACode(username, destination);
    }

    public boolean verify2FACode(String username, String code) {
        return twoFactorAuthPort.verify2FACode(username, code);
    }

    public boolean is2FAEnabled(String username) {
        return twoFactorAuthPort.is2FAEnabled(username);
    }

    public void enable2FA(String username) {
        twoFactorAuthPort.enable2FA(username);
    }

    public void disable2FA(String username) {
        twoFactorAuthPort.disable2FA(username);
    }
}

