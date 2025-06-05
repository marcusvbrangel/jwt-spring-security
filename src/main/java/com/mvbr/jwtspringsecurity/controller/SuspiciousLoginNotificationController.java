package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.SuspiciousLoginNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suspicious-login")
public class SuspiciousLoginNotificationController {
    private final SuspiciousLoginNotificationService notificationService;

    @Autowired
    public SuspiciousLoginNotificationController(SuspiciousLoginNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notify")
    public ResponseEntity<String> notifySuspiciousLogin(@RequestParam String username, @RequestParam String details) {
        notificationService.notify(username, details);
        return ResponseEntity.ok("Notificação de login suspeito enviada.");
    }
}

