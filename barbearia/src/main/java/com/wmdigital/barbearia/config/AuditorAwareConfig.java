package com.wmdigital.barbearia.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wmdigital.barbearia.entity.Usuario;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfig {
	
	
	 @Bean(name = "auditorAware")
	 public AuditorAware<String> auditorAware() {
	return () -> {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return Optional.of("system"); // fallback para jobs, testes, anon, etc.
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof Usuario u) {
            String nome = u.getUsername(); // ou u.getUsername()
            return Optional.ofNullable(nome);
        }
        return Optional.ofNullable(auth.getName()); // fallback
    };
}
	
	

}
