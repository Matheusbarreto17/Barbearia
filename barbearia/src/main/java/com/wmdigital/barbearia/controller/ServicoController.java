package com.wmdigital.barbearia.controller;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.ServicoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/servicos")
public class ServicoController extends AbstractController<Servico> {
	
	private final ServicoService servicoService;

	protected ServicoController(ServicoService servicoService) {
		super("servico", Servico :: new);
		
		this.servicoService = servicoService;
	}

	@Override
	protected Servico createEmptyEntity() {
		Servico servico = new Servico();
		servico.setAtivo(true);
		
		
		return servico;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		
		return super.listEntitys();
	}

	@Override
	protected AbstractEntityService<Servico> getService() {
		
		return servicoService;
	}
	
	@Override
	protected void afterCreateHook(Servico entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}
	
	@Override
	protected void afterUpDateHook(Servico entity, MultipartFile file, HttpServletRequest req) {
	
		handlerFoto(entity, file);
	}

	private void handlerFoto(Servico entity, MultipartFile file) {
		
	if(file != null && !file.isEmpty() && entity.getId() != null) {
			
			String url;
			try {
				url = servicoService.uploadFoto(entity.getId(), file);
				entity.setFoto(url);
				getService().edit(entity);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	}
}
