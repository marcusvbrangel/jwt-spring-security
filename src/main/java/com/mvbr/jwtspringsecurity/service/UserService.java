package com.mvbr.jwtspringsecurity.service;

import com.mvbr.jwtspringsecurity.config.security.jwt.JwtTokenService;
import com.mvbr.jwtspringsecurity.config.security.spring.SecurityConfig;
import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import com.mvbr.jwtspringsecurity.dto.CreateUserDto;
import com.mvbr.jwtspringsecurity.dto.LoginUserDto;
import com.mvbr.jwtspringsecurity.dto.RecoveryJwtTokenDto;
import com.mvbr.jwtspringsecurity.model.Role;
import com.mvbr.jwtspringsecurity.model.User;
import com.mvbr.jwtspringsecurity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public UserService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService,
                       UserRepository userRepository, SecurityConfig securityConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfig.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}