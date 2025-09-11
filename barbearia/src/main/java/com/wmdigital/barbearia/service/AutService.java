package com.wmdigital.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.dao.UsuarioRepository;
import com.wmdigital.barbearia.entity.Usuario;

@Service
public class AutService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario getUsuarioLogadoOrNull() {
		
		var ctx = SecurityContextHolder.getContext();
		
		if(ctx ==  null || ctx.getAuthentication() == null) return null;
		
		var aut = ctx.getAuthentication();
		
		if(!aut.isAuthenticated() || "anonymousUser".equals(aut.getPrincipal())) return null;
		
		String username = aut.getName();
		
		return usuarioRepository.findByUsername(username).orElse(null);
	}
	
	

}
