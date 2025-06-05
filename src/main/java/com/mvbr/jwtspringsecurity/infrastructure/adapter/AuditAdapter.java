package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.AuditPort;
import org.springframework.stereotype.Component;

@Component
public class AuditAdapter implements AuditPort {
    @Override
    public void logEvent(String username, String action, String details) {
        // Aqui você pode persistir em banco, enviar para um sistema de logs, etc.
        System.out.printf("AUDIT | Usuário: %s | Ação: %s | Detalhes: %s\n", username, action, details);
    }
}

