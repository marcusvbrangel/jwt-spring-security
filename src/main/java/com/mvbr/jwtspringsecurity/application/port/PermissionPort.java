package com.mvbr.jwtspringsecurity.application.port;

public interface PermissionPort {
    boolean hasPermission(String username, String permission);
    boolean hasRole(String username, String role);
    String[] getUserRoles(String username);
    String[] getUserPermissions(String username);
}
