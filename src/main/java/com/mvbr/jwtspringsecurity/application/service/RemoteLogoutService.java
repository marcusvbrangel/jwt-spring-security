package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.RemoteLogoutPort;

public class RemoteLogoutService {
    private final RemoteLogoutPort remoteLogoutPort;

    public RemoteLogoutService(RemoteLogoutPort remoteLogoutPort) {
        this.remoteLogoutPort = remoteLogoutPort;
    }

    public void forceLogout(String username) {
        remoteLogoutPort.forceLogout(username);
    }

    public boolean isUserLoggedOut(String username) {
        return remoteLogoutPort.isUserLoggedOut(username);
    }
}

