package com.wmdigital.barbearia.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wmdigital.barbearia.entity.Company;
import com.wmdigital.barbearia.framework.AbstractPhotoService;
import com.wmdigital.barbearia.util.StorageDomain;

@Service
public class CompanyPhotoService extends AbstractPhotoService<Company> {

	protected CompanyPhotoService(FileStoregeService storage) {
		super(storage);
	}

	@Override
	protected UUID getId(Company entity) {
		
		return entity.getId();
	}

	@Override
	protected StorageDomain domain() {
		
		return StorageDomain.COMPANYS;
	}

}
