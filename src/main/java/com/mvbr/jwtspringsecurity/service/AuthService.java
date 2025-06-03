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
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_USUARIO_BLOQUEADO_ATE;

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
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Autenticação manual para login e retorno de JWT
    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {

        Usuario usuario = findUserByEmailOrThrow(authenticateUserRequest.email());

        validatePasswordEncoderMatched(authenticateUserRequest, usuario);

        validateUserEnabled(usuario);

        validateUserNotLocked(usuario);

        resetFailedLogin(usuario.getEmail());

        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

        String token = jwtUtil.generateToken(userDetails);

        return new AuthenticateUserResponse(token);
    }

    private Usuario findUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException(MSG_EMAIL_OU_SENHA_INVALIDOS));
    }

    private void validatePasswordEncoderMatched(AuthenticateUserRequest authenticateUserRequest, Usuario usuario) {

        if (!passwordEncoder.matches(authenticateUserRequest.password(), usuario.getSenha())) {
            registerFailedLogin(usuario.getEmail());
            throw new BadCredentialsException(MSG_EMAIL_OU_SENHA_INVALIDOS);
        }

    }

    private static void validateUserEnabled(Usuario usuario) {
        if (!usuario.getEnabled()) {
            throw new BadCredentialsException(MSG_EMAIL_NAO_VERIFICADO);
        }
    }

    private void registerFailedLogin(String email) {

        Usuario usuario = userRepository.findByEmail(email).orElse(null);

        if (usuario == null)
            return;

        usuario.setFailedLoginAttempts(usuario.getFailedLoginAttempts() + 1);
        usuario.setLastFailedLoginAt(java.time.LocalDateTime.now());

        if (usuario.getFailedLoginAttempts() >= 5) {

            usuario.setLockedUntil(java.time.LocalDateTime.now().plusMinutes(15));
            this.sendUnlockEmail(usuario);

        }

        userRepository.save(usuario);

    }

    private void resetFailedLogin(String email) {

        Usuario usuario = userRepository.findByEmail(email).orElse(null);

        if (usuario == null)
            return;

        usuario.setFailedLoginAttempts(0);
        usuario.setLockedUntil(null);

        userRepository.save(usuario);
    }

    private void validateUserNotLocked(Usuario usuario) {

        if (usuario.getLockedUntil() != null && usuario.getLockedUntil().isAfter(java.time.LocalDateTime.now())) {

            throw new org.springframework.security.authentication.LockedException(
                    MSG_USUARIO_BLOQUEADO_ATE + usuario.getLockedUntil()
            );

        }
    }

    private void sendUnlockEmail(Usuario usuario) {

        String unlockLink = "http://localhost:8080/users/unlock?email=" + usuario.getEmail();

        String subject = "Sua conta foi bloqueada";

        String body = "Sua conta foi bloqueada por tentativas inválidas de login. Clique para desbloquear: " + unlockLink;

        emailService.send(usuario.getEmail(), subject, body);
    }

    public void unlockUser(String email) {

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setFailedLoginAttempts(0);
        usuario.setLockedUntil(null);

        userRepository.save(usuario);
    }

    public void startPasswordReset(String email) {

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("Usuário não encontrado"));

        String token = java.util.UUID.randomUUID().toString();

        usuario.setPasswordResetToken(token);
        usuario.setPasswordResetExpiresAt(java.time.LocalDateTime.now().plusHours(1));

        userRepository.save(usuario);

        emailService.send(email, "Recupere sua senha", "Clique: http://localhost:8080/users/redefinir-senha?token=" + token);
    }

    public void resetPassword(String token, String novaSenha) {

        Usuario usuario = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (usuario.getPasswordResetExpiresAt() == null || usuario.getPasswordResetExpiresAt().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado!");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setPasswordResetToken(null);
        usuario.setPasswordResetExpiresAt(null);

        userRepository.save(usuario);
    }

}
