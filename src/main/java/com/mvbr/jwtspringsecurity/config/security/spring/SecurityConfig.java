package com.mvbr.jwtspringsecurity.config.security.spring;

import com.mvbr.jwtspringsecurity.config.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

/**
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 * A classe SecurityConfig configura toda a segurança da sua aplicação Spring Boot usando JWT. Ela define:
 *
 *      - Quais endpoints exigem autenticação ou roles específicas:
 *        Usa arrays de URLs para liberar, exigir autenticação ou restringir por role (CUSTOMER, ADMINISTRATOR).
 *
 *      - Política de sessão:
 *        Define a aplicação como stateless (SessionCreationPolicy.STATELESS), ou seja, não usa sessão de servidor.
 *
 *      - Desabilita CSRF e CORS:
 *        CSRF e CORS são desabilitados porque a API é stateless e, em testes via Postman, não são necessários.
 *
 *      - Headers de segurança:
 *        Adiciona políticas de segurança HTTP, como Content-Security-Policy, Frame-Options e Referrer-Policy.
 *
 *      - Filtro JWT:
 *        Adiciona o filtro JwtAuthenticationFilter antes do filtro padrão do Spring, para validar o token JWT em cada requisição.
 *
 *      - Beans de autenticação e senha:
 *        Expõe beans de AuthenticationManager e PasswordEncoder (BCrypt) para uso em autenticação e cadastro de usuários.
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    public static final String HOLE_CUSTOMER = "CUSTOMER";

    public static final String HOLE_ADMINISTRATOR = "ADMINISTRATOR";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login", // Url que usaremos para fazer login
            "/users/confirm", // Confirmar/validar cadastro de usuario por email
            "/users/unlock", // Desbloquear usuario bloqueado por varias tentativas e erros
            "/users/redefinir", // Solicitar redefinir senha
            "/users/recuperar-senha" // Trocar a senha
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users/test"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users/test/customer"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMINISTRATOR = {
            "/users/test/administrator",
            "/users" // Url que usaremos para criar um usuário
    };

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                .csrf((csrf) -> csrf.disable())

                .cors((cors) -> cors.disable())

                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorizeHttpRequests) -> {

                    authorizeHttpRequests.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll();

                    authorizeHttpRequests.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated();

                    authorizeHttpRequests.requestMatchers(ENDPOINTS_CUSTOMER).hasRole(HOLE_CUSTOMER);

                    authorizeHttpRequests.requestMatchers(ENDPOINTS_ADMINISTRATOR).hasRole(HOLE_ADMINISTRATOR);

                    authorizeHttpRequests.anyRequest().denyAll();

                })

                .headers((headers) -> {
                    headers.xssProtection((xssProtection) -> xssProtection.disable());
                    headers.contentSecurityPolicy((contentSecurityPolicy) -> contentSecurityPolicy.policyDirectives("default-src 'self'"));
                    headers.frameOptions((frameOptions) -> frameOptions.sameOrigin());
                    headers.referrerPolicy((referrerPolicy) -> referrerPolicy.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER));
                })

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
