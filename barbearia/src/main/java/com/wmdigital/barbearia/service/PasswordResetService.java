package com.wmdigital.barbearia.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.dao.PasswordResetRepository;
import com.wmdigital.barbearia.dao.UsuarioRepository;
import com.wmdigital.barbearia.entity.PasswordReset;
import com.wmdigital.barbearia.entity.Usuario;

import jakarta.transaction.Transactional;


@Service
public class PasswordResetService {

	private final PasswordResetRepository passwordResetRepository;
	private final UsuarioRepository usuarioRepository;
	private final EmailService emailService;
	
	@Value("${app.base-url}")
	private String baseUrl;
	
	public PasswordResetService(PasswordResetRepository passwordResetRepository, UsuarioRepository usuarioRepository, EmailService emailService) {
		
		this.passwordResetRepository = passwordResetRepository;
		this.usuarioRepository = usuarioRepository;
		this.emailService = emailService;
		
	}
		@Transactional
		public void solicitarReset(String userNameorEmail) {
			
			Usuario usuario = usuarioRepository.findByUsername(userNameorEmail).orElseThrow(()->new IllegalArgumentException("Usuario não encontrado"));
			PasswordReset passwordReset = PasswordReset.fill(usuario, 60);
			passwordResetRepository.save(passwordReset);
			
			String link = baseUrl + "/resetar-senha?token=" + passwordReset.getToken();
			Map<String, Object> model = new HashMap<>();
			model.put("nome", usuario.getUsername());
			model.put("restUrl", link);
			model.put("expiraMin", 60);
			
			emailService.sendTemplate(usuario.getUsername(), "Redefinir Senha", "emails/password-reset", model);
		}
		
		@Transactional
		public void redefinirSenha(String token, String novaSenha, UsuarioService usarioService) {
			
			PasswordReset resetToken = passwordResetRepository.findByToken(token).orElseThrow(()->new IllegalArgumentException("Token invalido"));
			
			if (!resetToken.isValid()) {
				throw new IllegalStateException("Tokien usado ou expirado");
			}
			
			Usuario usuarioToken = resetToken.getusuario();
			usuarioToken.setPassword(novaSenha);
			usarioService.save(usuarioToken);
			resetToken.setUsed(true);
		}
}
