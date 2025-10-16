package com.wmdigital.barbearia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wmdigital.barbearia.dao.ClienteRepository;
import com.wmdigital.barbearia.entity.Cliente;
import com.wmdigital.barbearia.framework.AbstractEntityService;

@Service
@Transactional
public class ClienteService extends AbstractEntityService<Cliente> {
	
	private final ClienteRepository clienteDAO;

	protected ClienteService(ClienteRepository clienteDAO) {
		super(Cliente.class, "cliente");
		
		this.clienteDAO = clienteDAO;
	}

	@Override
	protected JpaRepository<Cliente, UUID> getDao() {
	
		return clienteDAO;
	}

	@Override
	protected void validateSave(Cliente entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Cliente entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Cliente entity) {
		
		return entity.getId().toString();
	}


	@Override
	public List<Cliente> findAllNew() {
		
		return clienteDAO.findAll();
	}
	
	public Cliente findByNome(String nome) {
		
		
		return clienteDAO.findByNome(nome).orElse(null);
	}

}
