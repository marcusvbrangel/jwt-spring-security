package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.UserRegistrationOrchestrationService;
import com.mvbr.jwtspringsecurity.application.service.AuthService;
import com.mvbr.jwtspringsecurity.application.service.EmailConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRegistrationOrchestrationService userRegistrationService;
    private final AuthService authService;
    private final EmailConfirmationService emailConfirmationService;

    @Autowired
    public AuthController(UserRegistrationOrchestrationService userRegistrationService,
                         AuthService authService,
                         EmailConfirmationService emailConfirmationService) {
        this.userRegistrationService = userRegistrationService;
        this.authService = authService;
        this.emailConfirmationService = emailConfirmationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        userRegistrationService.registerUser(username, email, password);
        return ResponseEntity.ok("Usuário registrado. Confirme seu e-mail.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String jwt = authService.login(username, password);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        boolean confirmed = emailConfirmationService.confirmEmail(token);
        if (confirmed) {
            return ResponseEntity.ok("E-mail confirmado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken) {
        String jwt = authService.refreshSession(refreshToken);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String username, @RequestParam String token) {
        authService.logout(username, token);
        return ResponseEntity.ok("Logout realizado com sucesso.");
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<String> verify2FA(@RequestParam String username, @RequestParam String code) {
        boolean verified = authService.verify2FA(username, code);
        if (verified) {
            return ResponseEntity.ok("2FA verificado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Código 2FA inválido.");
        }
    }
}
