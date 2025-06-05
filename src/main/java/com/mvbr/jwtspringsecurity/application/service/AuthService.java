package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.*;

public class AuthService {
    private final AuthPort authPort;
    private final LoginAttemptPort loginAttemptPort;
    private final JwtBlacklistPort jwtBlacklistPort;
    private final RefreshTokenPort refreshTokenPort;
    private final TwoFactorAuthPort twoFactorAuthPort;
    private final AuditPort auditPort;
    private final PermissionPort permissionPort;
    private final RemoteLogoutPort remoteLogoutPort;
    private final SuspiciousLoginNotificationPort suspiciousLoginNotificationPort;

    public AuthService(AuthPort authPort,
                       LoginAttemptPort loginAttemptPort,
                       JwtBlacklistPort jwtBlacklistPort,
                       RefreshTokenPort refreshTokenPort,
                       TwoFactorAuthPort twoFactorAuthPort,
                       AuditPort auditPort,
                       PermissionPort permissionPort,
                       RemoteLogoutPort remoteLogoutPort,
                       SuspiciousLoginNotificationPort suspiciousLoginNotificationPort) {
        this.authPort = authPort;
        this.loginAttemptPort = loginAttemptPort;
        this.jwtBlacklistPort = jwtBlacklistPort;
        this.refreshTokenPort = refreshTokenPort;
        this.twoFactorAuthPort = twoFactorAuthPort;
        this.auditPort = auditPort;
        this.permissionPort = permissionPort;
        this.remoteLogoutPort = remoteLogoutPort;
        this.suspiciousLoginNotificationPort = suspiciousLoginNotificationPort;
    }

    public String login(String username, String password) {
        if (loginAttemptPort.isAccountLocked(username)) {
            throw new IllegalStateException("Account is locked");
        }
        boolean authenticated = authPort.authenticate(username, password);
        loginAttemptPort.recordLoginAttempt(username, authenticated);
        if (!authenticated) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (twoFactorAuthPort.is2FAEnabled(username)) {
            twoFactorAuthPort.send2FACode(username, username); // destination pode ser email ou telefone
            throw new IllegalStateException("2FA required");
        }
        String jwt = authPort.generateJwtToken(username);
        auditPort.logEvent(username, "LOGIN", "User logged in");
        return jwt;
    }

    public boolean verify2FA(String username, String code) {
        boolean verified = twoFactorAuthPort.verify2FACode(username, code);
        if (verified) {
            auditPort.logEvent(username, "2FA_SUCCESS", "2FA verified");
        } else {
            auditPort.logEvent(username, "2FA_FAIL", "2FA failed");
        }
        return verified;
    }

    public void logout(String username, String token) {
        jwtBlacklistPort.blacklistToken(token);
        authPort.logout(username);
        auditPort.logEvent(username, "LOGOUT", "User logged out");
    }

    public String refreshSession(String refreshToken) {
        if (!refreshTokenPort.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String username = authPort.getAuthenticatedUsername();
        String newJwt = authPort.generateJwtToken(username);
        auditPort.logEvent(username, "REFRESH_TOKEN", "Session refreshed");
        return newJwt;
    }

    public void forceLogout(String username) {
        remoteLogoutPort.forceLogout(username);
        auditPort.logEvent(username, "FORCE_LOGOUT", "User forcibly logged out");
    }

    public boolean hasPermission(String username, String permission) {
        return permissionPort.hasPermission(username, permission);
    }

    public void notifySuspiciousLogin(String username, String details) {
        suspiciousLoginNotificationPort.notifySuspiciousLogin(username, details);
        auditPort.logEvent(username, "SUSPICIOUS_LOGIN", details);
    }
}

