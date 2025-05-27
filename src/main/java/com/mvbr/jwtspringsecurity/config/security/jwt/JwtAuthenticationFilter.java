package com.mvbr.jwtspringsecurity.config.security.jwt;

import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import com.mvbr.jwtspringsecurity.model.User;
import com.mvbr.jwtspringsecurity.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
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

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {

                String username = jwtTokenService.getSubjectFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    User user = userRepository.findByEmail(username).orElse(null);

                    if (user != null) {

                        UserDetails userDetails = new UserDetailsImpl(user);

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }

                }

            } catch (RuntimeException ex) {
                System.out.println("Token inválido ou expirado: " + ex.getMessage());
                // Apenas loga o erro e segue sem autenticar
            }

        }

        filterChain.doFilter(request, response);

    }

}
