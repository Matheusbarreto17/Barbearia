package com.wmdigital.barbearia.controller;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Produto;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/produtos")
public class ProdutoController extends AbstractController<Produto> {
	
	private final ProdutoService  produtoService;
	

	protected ProdutoController(ProdutoService  produtoService) {
		super("produto", Produto :: new);
		
		this.produtoService = produtoService;
		
	}

	@Override
	protected Produto createEmptyEntity() {
		Produto produto = new Produto();
		produto.setAtivo(true);
		
		
		return produto;
	}
	
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		
		return super.listEntitys();
	}


	@Override
	protected AbstractEntityService<Produto> getService() {
		
		return produtoService;
	}
	
	@Override
	protected void afterCreateHook(Produto entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}
	
	@Override
	protected void afterUpDateHook(Produto entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}

	private void handlerFoto(Produto entity, MultipartFile file) {
		
		if(file != null && !file.isEmpty() && entity.getId() != null) {
			
			String url;
			try {
				url = produtoService.uploadFoto(entity.getId(), file);
				entity.setFoto(url);
				getService().edit(entity);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	}
}
