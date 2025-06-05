package com.mvbr.jwtspringsecurity.application.port;

public interface RemoteLogoutPort {
    void forceLogout(String username);
    boolean isUserLoggedOut(String username);
}
