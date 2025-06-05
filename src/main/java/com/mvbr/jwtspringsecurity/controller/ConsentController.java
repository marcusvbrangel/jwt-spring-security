package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.application.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consent")
public class ConsentController {
    private final ConsentService consentService;

    @Autowired
    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptConsent(@RequestParam String username, @RequestParam String consentType) {
        consentService.acceptConsent(username, consentType);
        return ResponseEntity.ok("Consentimento aceito.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectConsent(@RequestParam String username, @RequestParam String consentType) {
        consentService.rejectConsent(username, consentType);
        return ResponseEntity.ok("Consentimento rejeitado.");
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> hasConsented(@RequestParam String username, @RequestParam String consentType) {
        boolean consented = consentService.hasConsented(username, consentType);
        return ResponseEntity.ok(consented);
    }
}

