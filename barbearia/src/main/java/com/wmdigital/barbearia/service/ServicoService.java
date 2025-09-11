package com.wmdigital.barbearia.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wmdigital.barbearia.dao.ServicoRepository;
import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.framework.AbstractEntityService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicoService extends AbstractEntityService<Servico> {
	
	private final ServicoRepository servicoDAO;
	private final ServicoPhotoService servicoPhotoService;

	protected ServicoService(ServicoRepository servicoDAO, ServicoPhotoService servicoPhotoService) {
		super(Servico.class, "servico");
		
		this.servicoDAO = servicoDAO;
		this.servicoPhotoService = servicoPhotoService;
		
	}

	@Override
	protected JpaRepository<Servico, UUID> getDao() {
		
		return servicoDAO;
	}

	@Override
	protected void validateSave(Servico entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Servico entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Servico entity) {
		
		return entity.getId().toString();
	}

	@Override
	public List<Servico> findAllNew() {
		
		return servicoDAO.findAll();
	}
	
	@Transactional
	public String uploadFoto(UUID id, MultipartFile file) throws IOException {
	    Servico b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Servico não encontrado"));
	    String url = servicoPhotoService.save(b, file);
	    // opcional: persistir campo na entidade
	    // b.setFotoMainUrl(url);
	    // repo.save(b);
	    return url;
	}

	@Transactional
	public void removerFotos(UUID id) throws IOException {
	    Servico b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Servico não encontrado"));
	    servicoPhotoService.deleteAll(b);
	    // opcional: limpar campos e salvar
	    // b.setFotoMainUrl(null);
	    // b.setFotoThumbUrl(null);
	    // repo.save(b);
	}

}
