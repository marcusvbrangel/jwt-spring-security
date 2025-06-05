package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.RemoteLogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logout-remote")
public class RemoteLogoutController {
    private final RemoteLogoutService remoteLogoutService;

    @Autowired
    public RemoteLogoutController(RemoteLogoutService remoteLogoutService) {
        this.remoteLogoutService = remoteLogoutService;
    }

    @PostMapping("/force")
    public ResponseEntity<String> forceLogout(@RequestParam String username) {
        remoteLogoutService.forceLogout(username);
        return ResponseEntity.ok("Logout remoto forçado para: " + username);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> isUserLoggedOut(@RequestParam String username) {
        boolean loggedOut = remoteLogoutService.isUserLoggedOut(username);
        return ResponseEntity.ok(loggedOut);
    }
}

