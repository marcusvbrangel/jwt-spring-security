package com.mvbr.jwtspringsecurity.infrastructure.adapter;

import com.mvbr.jwtspringsecurity.application.port.AuthPort;
import org.springframework.stereotype.Component;

@Component
public class AuthAdapter implements AuthPort {
    @Override
    public boolean authenticate(String username, String password) {
        // Aqui você faria a autenticação real, por exemplo, consultando o banco de dados
        // ou delegando ao Spring Security. Exemplo simplificado:
        return "user".equals(username) && "password".equals(password);
    }

    @Override
    public String generateJwtToken(String username) {
        // Aqui você geraria um JWT real. Exemplo simplificado:
        return "jwt-token-for-" + username;
    }

    @Override
    public String getAuthenticatedUsername() {
        // Em um cenário real, você obteria o usuário autenticado do contexto de segurança
        return "user";
    }

    @Override
    public void logout(String username) {
        // Implementação de logout, se necessário
        System.out.println("Logout para: " + username);
    }
}

