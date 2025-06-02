package com.mvbr.jwtspringsecurity.service;

import com.mvbr.jwtspringsecurity.config.security.jwt.JwtUtil;
import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import com.mvbr.jwtspringsecurity.dto.AuthenticateUserRequest;
import com.mvbr.jwtspringsecurity.dto.AuthenticateUserResponse;
import com.mvbr.jwtspringsecurity.model.Usuario;
import com.mvbr.jwtspringsecurity.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_NAO_VERIFICADO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_OU_SENHA_INVALIDOS;

/**
 *
 * -------------------------------------------------------------------------------------
 *
 * A classe AuthService é responsável por autenticar usuários no momento do login.
 * Ela recebe as credenciais (e-mail e senha), valida se o usuário existe e se a senha está correta,
 * e em caso de sucesso, gera e retorna um token JWT para o cliente. Esse token será usado nas próximas
 * requisições para acessar endpoints protegidos.
 *
 * Resumindo, ela centraliza a lógica de login e geração do JWT, separando essa responsabilidade das demais
 * operações de usuário (como cadastro ou busca). Isso segue boas práticas de separação de responsabilidades
 * em aplicações Spring Boot com autenticação JWT.
 *
 * -------------------------------------------------------------------------------------
 *
 */

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Autenticação manual para login e retorno de JWT
    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {

        Usuario usuario = userRepository.findByEmail(authenticateUserRequest.email())
                .orElseThrow(() -> new BadCredentialsException(MSG_EMAIL_OU_SENHA_INVALIDOS));

        if (!passwordEncoder.matches(authenticateUserRequest.password(), usuario.getSenha())) {
            throw new BadCredentialsException(MSG_EMAIL_OU_SENHA_INVALIDOS);
        }

        if (!usuario.getEnabled()) {
            throw new BadCredentialsException(MSG_EMAIL_NAO_VERIFICADO);
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

        String token = jwtUtil.generateToken(userDetails);

        return new AuthenticateUserResponse(token);
    }

}
