package com.wmdigital.barbearia.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.framework.AbstractPhotoService;
import com.wmdigital.barbearia.util.StorageDomain;

@Service
public class BarbeiroPhotoService extends AbstractPhotoService<Barbeiro>{
	
	

	public BarbeiroPhotoService(FileStoregeService storage) {
		super(storage);
		
	}

	@Override
	protected UUID getId(Barbeiro entity) {
		// TODO Auto-generated method stub
		return entity.getId();
	}

	@Override
	protected StorageDomain  domain() {
		
		return StorageDomain.BARBEIROS;
	}
	
	

}
