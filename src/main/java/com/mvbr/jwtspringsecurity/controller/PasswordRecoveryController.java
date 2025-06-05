package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.PasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recover(@RequestParam String email) {
        passwordRecoveryService.initiateRecovery(email);
        return ResponseEntity.ok("Token de recuperação enviado para o e-mail.");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestParam String token, @RequestParam String newPassword) {
        passwordRecoveryService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}

