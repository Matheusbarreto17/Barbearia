package com.wmdigital.barbearia.controller;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Usuario;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.BarbeiroService;
import com.wmdigital.barbearia.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/barbeiros")
public class BarbeiroController extends AbstractController<Barbeiro> {
	
	private final BarbeiroService barbeiroService;
	private final UsuarioService usuarioService;

	
	protected BarbeiroController(BarbeiroService barbeiroService, UsuarioService usuarioService) {
		super("barbeiro", Barbeiro :: new);
		
		this.barbeiroService = barbeiroService;
		this.usuarioService = usuarioService;
		
	}

	@Override
	protected Barbeiro createEmptyEntity() {
		
		Barbeiro barbeiro = new Barbeiro();
		barbeiro.setAtivo(true);
		barbeiro.setUsuario(new Usuario());
		
		return barbeiro;
	}

	@Override
	protected AbstractEntityService<Barbeiro> getService() {
		
		return barbeiroService;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {

		return super.listEntitys();
	}
	
	@Override
	protected void preloadFormData(ModelMap modelMap) {
		
		modelMap.addAttribute("usuarios", usuarioService.findAll());
		
	}

	@Override
	protected void afterCreateHook(Barbeiro entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}

	@Override
	protected void afterUpDateHook(Barbeiro entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
		
	}
	
	private void handlerFoto(Barbeiro entity, MultipartFile file) {
		
		if(file != null && !file.isEmpty() && entity.getId() != null) {
			
			String url;
			try {
				url = barbeiroService.uploadFoto(entity.getId(), file);
				entity.setFoto(url);
				getService().edit(entity);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	}
}
