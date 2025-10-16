package com.wmdigital.barbearia.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wmdigital.barbearia.dao.AgendamentoRepository;
import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.entity.Usuario;
import com.wmdigital.barbearia.framework.AbstractEntityService;



@Service
@Transactional
public class AgendamentoService extends AbstractEntityService<Agendamento> {
	
	private final AgendamentoRepository agendamentoDAO;
	private final EmailService emailService;

	protected AgendamentoService(AgendamentoRepository agendamentoDAO, EmailService emailService) {
		super(Agendamento.class, "agendamento");
		
		this.agendamentoDAO = agendamentoDAO;
		this.emailService = emailService;
		
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
	
	public List<Agendamento> getAgendamentosByUsuer(Usuario usuario) {
		
		return agendamentoDAO.findByUsuarioId(usuario.getId());
	}

	public List<Agendamento> listAgendamentoDayBarber(UUID barbeiroId, LocalDate day) {
		
		LocalDateTime start = day.atStartOfDay();
		LocalDateTime end = day.plusDays(1).atStartOfDay();
		
		return agendamentoDAO.findAllByBarberAndOverlap(barbeiroId, start, end);
	}
	
	
	@Transactional
	public void mailConfirmation(Agendamento agendamento) {
		
		String link =  "/resetar-senha?token=" ;
		Map<String, Object> model = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		model.put("cliente", agendamento.getCliente().getNome());
		model.put("barbeiro", agendamento.getBarbeiro().getNome());
		model.put("servico", agendamento.getServico().getName());
		model.put("inicio", agendamento.getInicio().format(formatter));
		model.put("fim", agendamento.getFim().format(formatter));
		model.put("cancelar", link);
		
		emailService.sendTemplate(agendamento.getCliente().getEmail(), "Confirmação de Agendamento", "emails/agendamento-confirmacao", model);
	
}
}
