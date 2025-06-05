package com.mvbr.jwtspringsecurity.application.port;

public interface AuthPort {
    boolean authenticate(String username, String password);
    String generateJwtToken(String username);
    String getAuthenticatedUsername();
    void logout(String username);
}
