package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login-attempts")
public class LoginAttemptController {
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public LoginAttemptController(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/record")
    public ResponseEntity<String> recordLoginAttempt(@RequestParam String username, @RequestParam boolean success) {
        loginAttemptService.recordLoginAttempt(username, success);
        return ResponseEntity.ok("Tentativa registrada.");
    }

    @GetMapping("/locked")
    public ResponseEntity<Boolean> isAccountLocked(@RequestParam String username) {
        boolean locked = loginAttemptService.isAccountLocked(username);
        return ResponseEntity.ok(locked);
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockAccount(@RequestParam String username) {
        loginAttemptService.unlockAccount(username);
        return ResponseEntity.ok("Conta desbloqueada.");
    }
}

