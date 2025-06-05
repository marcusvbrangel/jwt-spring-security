package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.TwoFactorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/2fa")
public class TwoFactorAuthController {
    private final TwoFactorAuthService twoFactorAuthService;

    @Autowired
    public TwoFactorAuthController(TwoFactorAuthService twoFactorAuthService) {
        this.twoFactorAuthService = twoFactorAuthService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send2FACode(@RequestParam String username, @RequestParam String destination) {
        twoFactorAuthService.send2FACode(username, destination);
        return ResponseEntity.ok("Código 2FA enviado.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify2FACode(@RequestParam String username, @RequestParam String code) {
        boolean verified = twoFactorAuthService.verify2FACode(username, code);
        if (verified) {
            return ResponseEntity.ok("2FA verificado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Código 2FA inválido.");
        }
    }

    @PostMapping("/enable")
    public ResponseEntity<String> enable2FA(@RequestParam String username) {
        twoFactorAuthService.enable2FA(username);
        return ResponseEntity.ok("2FA habilitado.");
    }

    @PostMapping("/disable")
    public ResponseEntity<String> disable2FA(@RequestParam String username) {
        twoFactorAuthService.disable2FA(username);
        return ResponseEntity.ok("2FA desabilitado.");
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> is2FAEnabled(@RequestParam String username) {
        boolean enabled = twoFactorAuthService.is2FAEnabled(username);
        return ResponseEntity.ok(enabled);
    }
}

