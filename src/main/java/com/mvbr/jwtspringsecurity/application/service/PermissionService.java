package com.mvbr.jwtspringsecurity.application.service;

import com.mvbr.jwtspringsecurity.application.port.PermissionPort;

public class PermissionService {
    private final PermissionPort permissionPort;

    public PermissionService(PermissionPort permissionPort) {
        this.permissionPort = permissionPort;
    }

    public boolean hasPermission(String username, String permission) {
        return permissionPort.hasPermission(username, permission);
    }

    public boolean hasRole(String username, String role) {
        return permissionPort.hasRole(username, role);
    }

    public String[] getUserRoles(String username) {
        return permissionPort.getUserRoles(username);
    }

    public String[] getUserPermissions(String username) {
        return permissionPort.getUserPermissions(username);
    }
}

