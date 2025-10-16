package com.wmdigital.barbearia.controller;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Company;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.CompanyService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/companys")
public class CompanyController extends AbstractController<Company> {
	

	private final CompanyService companyService;

	protected CompanyController(CompanyService companyService) {
		super("company", Company :: new);
		
		this.companyService = companyService;
	}

	@Override
	protected Company createEmptyEntity() {
		
		Company company = new Company();
		company.setAtivo(true);
		
		
		return company;
	}

	@Override
	protected AbstractEntityService<Company> getService() {
	
		return companyService;
	}

	@GetMapping
	@Override
	public ModelAndView listEntitys() {

		return super.listEntitys();
	}
	
	@Override
	protected void afterCreateHook(Company entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}
	
	@Override
	protected void afterUpDateHook(Company entity, MultipartFile file, HttpServletRequest req) {
		
		handlerFoto(entity, file);
	}

	private void handlerFoto(Company entity, MultipartFile file) {
		
		if(file != null && !file.isEmpty() && entity.getId() != null) {
			
			String url;
			try {
				url = companyService.uploadFoto(entity.getId(), file);
				entity.setLogo(url);
				getService().edit(entity);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	}
}
