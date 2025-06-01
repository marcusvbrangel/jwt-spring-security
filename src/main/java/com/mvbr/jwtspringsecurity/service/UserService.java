package com.mvbr.jwtspringsecurity.service;

import com.mvbr.jwtspringsecurity.config.security.spring.UserDetailsImpl;
import com.mvbr.jwtspringsecurity.dto.CreateUserRequest;
import com.mvbr.jwtspringsecurity.dto.CreateUserResponse;
import com.mvbr.jwtspringsecurity.model.Role;
import com.mvbr.jwtspringsecurity.model.Usuario;
import com.mvbr.jwtspringsecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_EMAIL_JA_CADASTRADO;
import static com.mvbr.jwtspringsecurity.utils.constants.MessageConstants.MSG_USUARIO_NAO_ENCONTRADO;

/**
 *
 * -------------------------------------------------------------------------------------
 *
 * Sua classe UserService é responsável por duas funções principais no seu sistema: *
 *
 *  - Autenticação pelo Spring Security:
 *
 *          Implementa UserDetailsService, permitindo que o Spring busque usuários pelo e-mail (username) durante o login.
 *          O método loadUserByUsername retorna um objeto adaptado (UserDetailsImpl) para o contexto de segurança.
 *
 *
 *  - Cadastro de novos usuários:
 *
 *          Possui o método createUser, que valida se o e-mail já existe, codifica a senha, cria o usuário com a role
 *          informada e salva no banco de dados.
 *
 *
 * Resumindo: ela centraliza a lógica de busca e cadastro de usuários, integrando com o Spring Security e o repositório
 * de usuários.
 *
 * -------------------------------------------------------------------------------------
 *
 */

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Para autenticação pelo Spring Security
    // Serve para o Spring Security buscar o usuário pelo e-mail (username) durante a autenticação.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(MSG_USUARIO_NAO_ENCONTRADO + username));

        return new UserDetailsImpl(usuario);
    }

    // Criação de novo usuário
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new IllegalArgumentException(MSG_EMAIL_JA_CADASTRADO);
        }

        Role role = Role.builder()
                .name(createUserRequest.role())
                .build();

        Usuario usuario = new Usuario();
        usuario.setEmail(createUserRequest.email());
        usuario.setSenha(passwordEncoder.encode(createUserRequest.password()));
        usuario.setNome(createUserRequest.nome());
        usuario.setRoles(List.of(role));

        Usuario savedUser = userRepository.save(usuario);

        return new CreateUserResponse(savedUser.getEmail(), savedUser.getNome(), savedUser.getRoles());

    }

}
