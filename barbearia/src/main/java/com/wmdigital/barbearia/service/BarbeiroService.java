package com.wmdigital.barbearia.service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wmdigital.barbearia.dao.BarbeiroRepository;
import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.framework.AbstractEntityService;


@Service
public class BarbeiroService extends AbstractEntityService<Barbeiro> {
	
	private final BarbeiroRepository barbeiroDAO;
	private final BarbeiroPhotoService barbeiroPhotoService;

	protected BarbeiroService(BarbeiroRepository barbeiroDAO, BarbeiroPhotoService barbeiroPhotoService) {
		super(Barbeiro.class, "barbeiro");
		
		this.barbeiroDAO = barbeiroDAO;
		this.barbeiroPhotoService = barbeiroPhotoService;
	
	}

	@Override
	protected JpaRepository<Barbeiro, UUID> getDao() {
		
		return barbeiroDAO;
	}

	@Override
	protected void validateSave(Barbeiro entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Barbeiro entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Barbeiro entity) {
		
		return entity.getId().toString();
	}

	@Override
	public List<Barbeiro> findAllNew() {
		
		return barbeiroDAO.findAll();
	}
	
	@Transactional
	public String uploadFoto(UUID id, MultipartFile file) throws IOException {
	    Barbeiro b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));
	    String url = barbeiroPhotoService.save(b, file);
	    // opcional: persistir campo na entidade
	    // b.setFotoMainUrl(url);
	    // repo.save(b);
	    return url;
	}

	@Transactional
	public void removerFotos(UUID id) throws IOException {
	    Barbeiro b = getDao().findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));
	    barbeiroPhotoService.deleteAll(b);
	    // opcional: limpar campos e salvar
	    // b.setFotoMainUrl(null);
	    // b.setFotoThumbUrl(null);
	    // repo.save(b);
	}
}
