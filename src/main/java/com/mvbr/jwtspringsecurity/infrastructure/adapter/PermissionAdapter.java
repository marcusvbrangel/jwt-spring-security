package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.PermissionPort;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class PermissionAdapter implements PermissionPort {
    private final Map<String, Set<String>> userRoles = new HashMap<>();
    private final Map<String, Set<String>> userPermissions = new HashMap<>();

    public PermissionAdapter() {
        // Exemplo de dados em memória
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ADMIN");
        userRoles.put("admin", adminRoles);
        Set<String> adminPerms = new HashSet<>();
        adminPerms.add("READ");
        adminPerms.add("WRITE");
        userPermissions.put("admin", adminPerms);
    }

    @Override
    public boolean hasPermission(String username, String permission) {
        return userPermissions.getOrDefault(username, new HashSet<>()).contains(permission);
    }

    @Override
    public boolean hasRole(String username, String role) {
        return userRoles.getOrDefault(username, new HashSet<>()).contains(role);
    }

    @Override
    public String[] getUserRoles(String username) {
        return userRoles.getOrDefault(username, new HashSet<>()).toArray(new String[0]);
    }

    @Override
    public String[] getUserPermissions(String username) {
        return userPermissions.getOrDefault(username, new HashSet<>()).toArray(new String[0]);
    }
}

