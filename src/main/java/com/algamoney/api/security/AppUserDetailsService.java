package com.algamoney.api.security;

import com.algamoney.api.model.Usuario;
import com.algamoney.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return new UsuarioSistema(usuario, getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        return usuario.getPermissoes().stream().map(permissao ->
            new SimpleGrantedAuthority(permissao.getDescricao())
        ).collect(Collectors.toSet());
    }
}
