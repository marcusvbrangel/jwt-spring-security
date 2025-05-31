package com.mvbr.jwtspringsecurity.config.security.jwt;

import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_TOKEN_JWT_INVALIDO_OU_EXPIRADO;

/*
-------------------------------------------------------------------------------------
Em projetos com autenticação JWT no Spring Security, é necessário implementar um filtro personalizado para:

    * Interceptar as requisições;
    * Extrair e validar o token JWT do header Authorization;
    * Autenticar o usuário no contexto do Spring Security, caso o token seja válido.

Esse filtro normalmente estende OncePerRequestFilter e é registrado na cadeia de filtros do Spring Security.
Sem esse filtro, o Spring não saberá como autenticar usuários a partir do token JWT enviado nas requisições.

Filtro personalizado para verificar se o usuário é um usuário válido e autenticá-lo. O filtro usará o UserRepository e
JwtTokenService que implementamos para encontrar e verificar se o usuário é válido e autenticá-lo.
-------------------------------------------------------------------------------------
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, @Lazy UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /*
    -------------------------------------------------------------------------------------
    1. Extrai o token JWT do header Authorization.
    2. Valida o token.
    3. Busca o usuário pelo e-mail (subject do token).
    4. Autentica o usuário no contexto do Spring Security.
    5. Segue a cadeia de filtros.
    -------------------------------------------------------------------------------------
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Extrai o token do header Authorization: Bearer <token>...
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(MSG_TOKEN_JWT_INVALIDO_OU_EXPIRADO);
                return;
            }
        }

        // Se encontrou username e contexto ainda não está autenticado...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, (UserDetailsImpl) userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(MSG_TOKEN_JWT_INVALIDO_OU_EXPIRADO);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

}
