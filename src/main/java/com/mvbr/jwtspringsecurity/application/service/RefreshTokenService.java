package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.RefreshTokenPort;

public class RefreshTokenService {
    private final RefreshTokenPort refreshTokenPort;

    public RefreshTokenService(RefreshTokenPort refreshTokenPort) {
        this.refreshTokenPort = refreshTokenPort;
    }

    public String createRefreshToken(String username) {
        return refreshTokenPort.createRefreshToken(username);
    }

    public boolean validateRefreshToken(String token) {
        return refreshTokenPort.validateRefreshToken(token);
    }

    public void revokeRefreshToken(String token) {
        refreshTokenPort.revokeRefreshToken(token);
    }
}

