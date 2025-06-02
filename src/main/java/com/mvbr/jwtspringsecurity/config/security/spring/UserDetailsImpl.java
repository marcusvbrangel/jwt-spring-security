package com.mvbr.jwtspringsecurity.config.security.spring;

import com.mvbr.jwtspringsecurity.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * -------------------------------------------------------------------------------------------------------------------
 *
 * Por que criar uma classe customizada que implementa UserDetails?
 *
 *      - Flexibilidade: Você pode adicionar qualquer campo que julgar necessário (ex: id, email, nome, roles,
 *        claims extras, status, etc).
 *
 *      - Integração fácil: Spring Security exige apenas que o objeto implementa a interface UserDetails. Assim,
 *        seu objeto pode ser adaptado ao padrão do Spring, mas ainda representa fielmente o seu usuário.
 *
 *      - Organização: Separar a entidade do banco (Usuario) do objeto de autenticação (MeuUserDetailsImpl) deixa o
 *        código mais limpo e desacoplado.
 *
 *      - Evita problemas: Usar a entidade do banco diretamente pode causar problemas de serialização, lazy loading, etc.
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 * Como normalmente é feito?
 *
 *      - Você tem sua entidade principal (ex: Usuario, mapeada pelo JPA).
 *
 *      - Cria uma classe, tipo CustomUserDetails ou MyUserDetailsImpl, que implementa UserDetails e recebe a entidade
 *        no construtor.
 *
 *      - Assim, você pode expor apenas o que quiser para autenticação, sem expor toda a entidade do banco.
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 * Você pode expor mais atributos da sua classe Usuario na implementação do UserDetailsImpl. Basta adicionar os campos
 * desejados como atributos na sua classe customizada e inicializá-los no construtor. Assim, você terá acesso a esses
 * dados no contexto de autenticação do Spring Security, sem expor toda a entidade JPA.
 *
 * Exemplo: se quiser expor o nome do usuário, adicione um campo nome e um getter correspondente em UserDetailsImpl.
 * Isso não afeta a segurança, desde que você não exponha informações sensíveis desnecessárias.
 *
 * -------------------------------------------------------------------------------------------------------------------
 *
 */

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String senha;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nome;
    private final String sobrenome;
    private final String telefone;
    private final boolean enabled;

    public UserDetailsImpl(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.telefone = usuario.getTelefone();
        this.authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        this.enabled = usuario.getEnabled();

    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
