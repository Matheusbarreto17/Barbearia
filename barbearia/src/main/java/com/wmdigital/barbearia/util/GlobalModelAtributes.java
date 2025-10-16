package com.wmdigital.barbearia.util;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.wmdigital.barbearia.entity.Company;
import com.wmdigital.barbearia.service.CompanyService;

@ControllerAdvice
public class GlobalModelAtributes {
	
	private final CompanyService companyService;
	
	public GlobalModelAtributes(CompanyService companyService) {
		this.companyService = companyService;
	}

    @ModelAttribute("company")
    public Company company() {
        
        return companyService.getMainCompany(); 
       
    }
}

