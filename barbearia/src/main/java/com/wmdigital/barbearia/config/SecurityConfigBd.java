package com.wmdigital.barbearia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity 
public class SecurityConfigBd {

	
	 	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	          .authorizeHttpRequests(auth -> auth
	              .requestMatchers(
	                  "/", "/public/**",
	                  "/css/**", "/js/**", "/img/**", "/webjars/**",
	                  "/login", "/error"
	              ).permitAll()
	              .anyRequest().authenticated()
	          )
	          .formLogin(login -> login
	              .loginPage("/login")                 // sua página de login (GET)
	              .loginProcessingUrl("/login")        // POST do formulário
	              .defaultSuccessUrl("/dashboard", true)        // para onde vai ao logar
	              .failureUrl("/login?error")          // volta com erro
	              .permitAll()
	          )
	          .logout(logout -> logout
	              .logoutUrl("/logout")
	              .logoutSuccessUrl("/login?logout")
	              .invalidateHttpSession(true)
	              .deleteCookies("JSESSIONID")
	              .permitAll()
	          );

	        // Em dev/teste, se for fazer POST sem token CSRF fora de formulários:
	        // http.csrf(csrf -> csrf.disable());

	        return http.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        // BCrypt com fator 10 (OK para dev). Em prod, 10–12 conforme CPU.
	        return new BCryptPasswordEncoder(10);
	    }

}
