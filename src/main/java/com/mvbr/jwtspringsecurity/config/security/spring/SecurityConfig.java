package com.mvbr.jwtspringsecurity.config.security.spring;

import com.mvbr.jwtspringsecurity.config.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login", // Url que usaremos para fazer login
            "/users" // Url que usaremos para criar um usuário
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
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/administrator"
    };

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*
    -------------------------------------------------------------------------------------
    O método securityFilterChain(HttpSecurity httpSecurity) serve para configurar as regras de segurança da sua
    aplicação Spring Boot usando Spring Security. Ele define:

    * Quais endpoints exigem autenticação ou permissão específica.
    * Como as sessões são gerenciadas (no seu caso, stateless, ideal para JWT).
    * Quais filtros personalizados (como o filtro JWT) devem ser aplicados e em que ordem.
    * Outras configurações de segurança, como desabilitar CSRF.

    No final, ele retorna um objeto SecurityFilterChain que o Spring usa para proteger as rotas da aplicação conforme
    as regras definidas.
    -------------------------------------------------------------------------------------
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf().disable() // Desativa a proteção contra CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura a política de criação de sessão como stateless
                .and().authorizeHttpRequests() // Habilita a autorização para as requisições HTTP
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR") // Repare que não é necessário colocar "ROLE" antes do nome, como fizemos na definição das roles
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                .anyRequest().denyAll()
                // Adiciona o filtro de autenticação de usuário que criamos, antes do filtro de segurança padrão do Spring Security
                .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /*
    -------------------------------------------------------------------------------------
    Esse método serve para expor um bean do tipo AuthenticationManager no contexto do Spring. O AuthenticationManager
    é o componente central do Spring Security responsável por processar as autenticações (login).

    Ao declarar esse método com @Bean, você permite que outras partes da aplicação (como controllers ou serviços)
    possam injetar e usar o AuthenticationManager para autenticar usuários manualmente, caso necessário.

    No seu código, ele apenas delega a criação do AuthenticationManager para o próprio Spring, usando a configuração
    padrão (AuthenticationConfiguration). Isso é útil, por exemplo, para autenticar usuários em endpoints de
    login customizados.
    -------------------------------------------------------------------------------------
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    -------------------------------------------------------------------------------------
    O método public PasswordEncoder passwordEncoder() serve para expor um bean do tipo PasswordEncoder no contexto
    do Spring. Ele define qual algoritmo será usado para codificar (criptografar) e verificar senhas dos usuários,
    garantindo que as senhas não fiquem salvas em texto puro no banco de dados.
    No seu caso, retorna uma instância de BCryptPasswordEncoder, que é uma das formas mais seguras e recomendadas
    para armazenar senhas.
    -------------------------------------------------------------------------------------
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
