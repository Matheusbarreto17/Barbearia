package com.wmdigital.barbearia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wmdigital.barbearia.dao.AgendamentoRepository;
import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.framework.AbstractEntityService;

@Service
@Transactional
public class AgendamentoService extends AbstractEntityService<Agendamento> {
	
	private final AgendamentoRepository agendamentoDAO;

	protected AgendamentoService(AgendamentoRepository agendamentoDAO) {
		super(Agendamento.class, "agendamento");
		
		this.agendamentoDAO = agendamentoDAO;
		
	}

	@Override
	protected JpaRepository<Agendamento, UUID> getDao() {
	
		return agendamentoDAO;
	}

	@Override
	protected void validateSave(Agendamento entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Agendamento entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Agendamento entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agendamento> findAllNew() {
		
		return agendamentoDAO.findAll();
	}

}
