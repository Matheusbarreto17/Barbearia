package com.wmdigital.barbearia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wmdigital.barbearia.dao.RoleRepository;
import com.wmdigital.barbearia.dao.UsuarioRepository;
import com.wmdigital.barbearia.entity.Role;
import com.wmdigital.barbearia.entity.Usuario;

import lombok.RequiredArgsConstructor;


@Component
public class DataInitial implements CommandLineRunner{
	
    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final PasswordEncoder encoder;	
    
    
    
    public DataInitial(RoleRepository roleRepository, UsuarioRepository usuarioRepository,PasswordEncoder encoder) {
			this.roleRepository = roleRepository;
			this.usuarioRepository = usuarioRepository;
			this.encoder = encoder;
    }
    

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		
		//versao para quando o lombok estiver ok 
		
//		Role admin = roleRepository.findByName("ROLE_ADMIN")
//			    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));
//
//			usuarioRepository.findByUsername("admin").orElseGet(() -> {
//			    Usuario u = Usuario.builder()
//			        .username("admin")
//			        .password(encoder.encode("123"))
//			        .role(admin)
//			        .build();
//			    return usuarioRepository.save(u);
//			});
		
		
		
		 // ROLE_ADMIN
        Role admin = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        if (admin == null) {
            admin = new Role();
            admin.setName("ROLE_ADMIN");
            admin = roleRepository.save(admin);
        }
        

        // Usuário admin
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario u = new Usuario();
            u.setUsername("admin");
            u.setPassword(encoder.encode("123"));
            u.setRole(admin);
            usuarioRepository.save(u);
        }
        
        Role barber = roleRepository.findByName("ROLE_BARBER").orElse(null);
        if (barber == null) {
        	barber = new Role();
        	barber.setName("ROLE_BARBER");
        	barber = roleRepository.save(barber);
        }
        
        if (usuarioRepository.findByUsername("barber").isEmpty()) {
            Usuario userbarber = new Usuario();
            userbarber.setUsername("barber");
            userbarber.setPassword(encoder.encode("123"));
            userbarber.setRole(barber);
            usuarioRepository.save(userbarber);
        }
        
        Role client = roleRepository.findByName("ROLE_CLIENT").orElse(null);
        if (client == null) {
        	client = new Role();
        	client.setName("ROLE_CLIENT");
        	client = roleRepository.save(client);
        }
        
        if (usuarioRepository.findByUsername("client").isEmpty()) {
            Usuario userclient = new Usuario();
            userclient.setUsername("client");
            userclient.setPassword(encoder.encode("123"));
            userclient.setRole(client);
            usuarioRepository.save(userclient);
        }
    }
}
