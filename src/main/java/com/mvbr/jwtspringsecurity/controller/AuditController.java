package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("/log")
    public ResponseEntity<String> logEvent(@RequestParam String username, @RequestParam String action, @RequestParam String details) {
        auditService.logEvent(username, action, details);
        return ResponseEntity.ok("Evento de auditoria registrado.");
    }
}

