package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.JwtBlacklistPort;

public class JwtBlacklistService {
    private final JwtBlacklistPort jwtBlacklistPort;

    public JwtBlacklistService(JwtBlacklistPort jwtBlacklistPort) {
        this.jwtBlacklistPort = jwtBlacklistPort;
    }

    public void blacklistToken(String token) {
        jwtBlacklistPort.blacklistToken(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return jwtBlacklistPort.isTokenBlacklisted(token);
    }
}

