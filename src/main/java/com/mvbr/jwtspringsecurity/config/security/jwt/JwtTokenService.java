package com.mvbr.jwtspringsecurity.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

/*
-------------------------------------------------------------------------------------
Classe para gerar um token e recuperar um usuário a partir de um token...
-------------------------------------------------------------------------------------
 */

@Service
public class JwtTokenService {

    // Chave secreta utilizada para gerar e verificar o token...
    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    // Emissor do token...
    private static final String ISSUER = "jws-sec-api";

    public String generateToken(UserDetailsImpl user) {

        try {

            // // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida...
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer(ISSUER) // Emissor to token...
                    .withIssuedAt(creationDate()) // Data emissao do token...
                    .withExpiresAt(expirationDate()) // Data de expiracao do token...
                    .withSubject(user.getUsername()) // Nome/email do usuario do token...
                    .withClaim("ano", "2025")
                    .withClaim("roles", user.getUser().getRoles() // claim extra com os papéis do usuário...
                            .stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toList()))
                    .sign(algorithm); // Assina o token usando o algoritmo especificado...

        } catch (JWTCreationException ex) {
            throw new JWTCreationException("Falha ao gerar o token. ", ex);
        }

    }

    public String getSubjectFromToken(String token) {

        try {

            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida...
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // emissor do token...
                    .build()
                    .verify(token) // Verifica a validade do token...
                    .getSubject(); // Obtem o nome/email do usuario do token...

        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }

    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return Instant.now().plusMillis(432000000); // 5 dias em milissegundos...
    }


}
