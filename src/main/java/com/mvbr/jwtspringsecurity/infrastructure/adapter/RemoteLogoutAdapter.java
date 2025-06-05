package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.RemoteLogoutPort;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RemoteLogoutAdapter implements RemoteLogoutPort {
    private final Set<String> loggedOutUsers = ConcurrentHashMap.newKeySet();

    @Override
    public void forceLogout(String username) {
        loggedOutUsers.add(username);
        System.out.println("Logout remoto forçado para: " + username);
    }

    @Override
    public boolean isUserLoggedOut(String username) {
        return loggedOutUsers.contains(username);
    }
}

