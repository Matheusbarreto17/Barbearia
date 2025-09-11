package com.wmdigital.barbearia.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wmdigital.barbearia.dao.UsuarioRepository;
import com.wmdigital.barbearia.entity.Usuario;

@Service
public class JpaUserDetailsService implements UserDetailsService { // padronizei o nome

    private final UsuarioRepository usuarioRepository;

    public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String roleName = u.getRole().getName();
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        return User.withUsername(u.getUsername())
            .password(u.getPassword())        // já codificada com BCrypt
            .authorities(roleName)            // um único papel
            .accountLocked(false)
            .accountExpired(false)
            .credentialsExpired(false)
            .disabled(!u.isEnabled())         // ajuste se seu campo tiver outro nome
            .build();
    }
}
