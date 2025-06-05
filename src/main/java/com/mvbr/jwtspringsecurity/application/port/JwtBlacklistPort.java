package com.mvbr.jwtspringsecurity.application.port;

public interface JwtBlacklistPort {
    void blacklistToken(String token);
    boolean isTokenBlacklisted(String token);
}
