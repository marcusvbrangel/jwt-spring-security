package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/has-permission")
    public ResponseEntity<Boolean> hasPermission(@RequestParam String username, @RequestParam String permission) {
        boolean has = permissionService.hasPermission(username, permission);
        return ResponseEntity.ok(has);
    }

    @GetMapping("/has-role")
    public ResponseEntity<Boolean> hasRole(@RequestParam String username, @RequestParam String role) {
        boolean has = permissionService.hasRole(username, role);
        return ResponseEntity.ok(has);
    }

    @GetMapping("/roles")
    public ResponseEntity<String[]> getUserRoles(@RequestParam String username) {
        return ResponseEntity.ok(permissionService.getUserRoles(username));
    }

    @GetMapping("/permissions")
    public ResponseEntity<String[]> getUserPermissions(@RequestParam String username) {
        return ResponseEntity.ok(permissionService.getUserPermissions(username));
    }
}

