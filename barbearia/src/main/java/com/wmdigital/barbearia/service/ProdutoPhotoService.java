package com.wmdigital.barbearia.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.entity.Produto;
import com.wmdigital.barbearia.framework.AbstractPhotoService;
import com.wmdigital.barbearia.util.StorageDomain;

@Service
public class ProdutoPhotoService extends AbstractPhotoService<Produto>{
	
	

	public ProdutoPhotoService(FileStoregeService storage) {
		super(storage);
		
	}

	@Override
	protected UUID getId(Produto entity) {
		return entity.getId();
	}

	@Override
	protected StorageDomain  domain() {	
		return StorageDomain.PRODUTOS;
	}
	
	

}
