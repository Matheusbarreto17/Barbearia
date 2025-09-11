//package com.wmdigital.barbearia.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//	
//	  @Bean
//	  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http
//	      .authorizeHttpRequests(auth -> auth
//	        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
//	        .anyRequest().authenticated()
//	      )
//	      .formLogin(Customizer.withDefaults()) // /login padrão
//	      .logout(Customizer.withDefaults());   // /logout padrão
//	      // .csrf(csrf -> csrf.disable());     // só se precisar desabilitar CSRF em dev/POSTs sem token
//	    return http.build();
//	  }
//
//	  @Bean
//	  UserDetailsService userDetailsService(PasswordEncoder encoder) {
//	    return new InMemoryUserDetailsManager(
//	      User.withUsername("admin").password(encoder.encode("123")).roles("ADMIN").build()
//	    );
//	  }
//
//
//}
