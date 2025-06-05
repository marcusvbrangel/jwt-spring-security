package com.mvbr.jwtspringsecurity.application.port;

public interface RefreshTokenPort {
    String createRefreshToken(String username);
    boolean validateRefreshToken(String token);
    void revokeRefreshToken(String token);
}
