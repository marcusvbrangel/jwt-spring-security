package com.mvbr.jwtspringsecurity.config.security.spring;

import com.mvbr.jwtspringsecurity.model.User;
import com.mvbr.jwtspringsecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    -------------------------------------------------------------------------------------
    O método loadUserByUsername() é um método da interface UserDetailsService, e é usado para carregar os detalhes
    do usuário com base no nome de usuário fornecido. Esse método é chamado automaticamente pelo Spring durante o
    processo de autenticação, e é responsável por retornar um UserDetails com base no nome de usuário fornecido.
    -------------------------------------------------------------------------------------
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserDetailsImpl(user);

    }

}
