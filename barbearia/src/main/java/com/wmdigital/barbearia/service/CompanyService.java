package com.wmdigital.barbearia.service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wmdigital.barbearia.dao.CompanyRepository;
import com.wmdigital.barbearia.entity.Company;
import com.wmdigital.barbearia.framework.AbstractEntityService;

import jakarta.transaction.Transactional;

@Service
public class CompanyService extends AbstractEntityService<Company> {
	
	private final CompanyRepository companyDAO;
	private final CompanyPhotoService companyPhotoService;

	
	protected CompanyService(CompanyRepository companyDAO, CompanyPhotoService companyPhotoService) {
		super(Company.class, "company");
		
		this.companyDAO = companyDAO;
		this.companyPhotoService = companyPhotoService;
	
	}

	@Override
	protected JpaRepository<Company, UUID> getDao() {
		
		return companyDAO;
	}

	@Override
	protected void validateSave(Company entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Company entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Company entity) {
		
		return entity.getId().toString();
	}

	@Override
	public List<Company> findAllNew() {
		
		return getDao().findAll();
	}
	
	@Transactional
	public String uploadFoto(UUID id, MultipartFile file) throws IOException {
	    Company b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Company não encontrado"));
	    String url = companyPhotoService.save(b, file);
	    return url;
	}

	@Transactional
	public void removerFotos(UUID id) throws IOException {
	    Company b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Company não encontrado"));
	    companyPhotoService.deleteAll(b);
	}

	public Company getMainCompany() {
		
		return getDao().findAll().get(0);
	}
	

}
