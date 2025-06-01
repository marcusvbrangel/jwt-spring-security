package com.mvbr.jwtspringsecurity.config.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import com.mvbr.jwtspringsecurity.exception.TokenExpiradoException;
import com.mvbr.jwtspringsecurity.exception.TokenInvalidoException;
import com.mvbr.jwtspringsecurity.utils.constants.MessageConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_TOKEN_JWT_EXPIRADO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_TOKEN_JWT_INVALIDO;

/**
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 * A classe JwtUtil centraliza toda a lógica relacionada ao JWT (JSON Web Token) na sua aplicação. Ela serve para:
 *
 *      - Gerar tokens JWT: Cria tokens assinados contendo informações do usuário (como username, id, nome e roles) e
 *        define o tempo de expiração.
 *
 *      - Validar tokens: Verifica se o token é válido, se a assinatura está correta, se o emissor bate e se não está
 *        expirado.
 *
 *      - Extrair informações: Permite obter dados do token, como username, id do usuário e data de expiração.
 *
 *      - Lançar exceções customizadas: Diferencia token expirado de token inválido, facilitando o tratamento de erros.
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 *  Implemente primeiro o JwtUtil
 *
 *      - O JwtAuthenticationFilter depende do JwtUtil para extrair, validar e manipular o JWT.
 *
 *      - O JwtUtil encapsula a lógica de geração/validação do token, extração de claims, username, expiração etc.
 *
 *      - Sem o JwtUtil, o filtro não consegue autenticar ninguém!
 *
 *  -------------------------------------------------------------------------------------------------------------------
 *
 */

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "yQw1v8nK3pL6sT9zB2eR5uH7jM0xC4aFqW8dZ1tS6gV3bN5mP0rX2cE7hU9kL3oQ";
    public static final String ISSUER = "jwt-spring-security";

    @Value("${jwt.expiration}")
    private long expirationMillis;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                .withClaim("nome", userDetails.getNome())
                .withClaim("id", userDetails.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMillis))
                .withIssuer(ISSUER)
                .sign(getAlgorithm());
    }

    public boolean validateToken(String token, UserDetailsImpl userDetails) {
        try {
            DecodedJWT jwt = decodedJWT(token);
            boolean usernameOk = jwt.getSubject().equals(userDetails.getUsername());
            boolean notExpired = jwt.getExpiresAt().after(new Date());
            return usernameOk && notExpired;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return decodedJWT(token).getSubject();
    }

    public Long extractUserId(String token) {
        return decodedJWT(token).getClaim("id").asLong();
    }

    public Date extractExpiration(String token) {
        return decodedJWT(token).getExpiresAt();
    }

    // Método privado para centralizar a verificação e decodificação do token...
    // Se o token for inválido, expirado ou adulterado, uma exceção será lançada...
    // Portanto, além de decodificar, o método já faz a validação básica do token....
    private DecodedJWT decodedJWT(String token) throws TokenInvalidoException {
        try {
            return JWT.require(getAlgorithm()).withIssuer(ISSUER).build().verify(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiradoException(MSG_TOKEN_JWT_EXPIRADO);
        } catch (JWTVerificationException e) {
            throw new TokenInvalidoException(MSG_TOKEN_JWT_INVALIDO);
        }
    }

}
