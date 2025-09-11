//package com.wmdigital.barbearia.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class PasswordEncoderBean {
//	
//		@Bean
//	    public PasswordEncoder passwordEncoder() {
//	        // fator 10 é bom p/ dev; em prod considere 10–12 (custo x desempenho)
//		    return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	    }
//
//}
