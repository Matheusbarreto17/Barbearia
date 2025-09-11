package com.wmdigital.barbearia.service;

import java.util.UUID;

import org.springframework.stereotype.Service;


import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.framework.AbstractPhotoService;
import com.wmdigital.barbearia.util.StorageDomain;

@Service
public class ServicoPhotoService extends AbstractPhotoService<Servico>{
	
	

	public ServicoPhotoService(FileStoregeService storage) {
		super(storage);
		
	}

	@Override
	protected UUID getId(Servico entity) {
		// TODO Auto-generated method stub
		return entity.getId();
	}

	@Override
	protected StorageDomain  domain() {
		
		return StorageDomain.SERVICOS;
	}
	
	

}
