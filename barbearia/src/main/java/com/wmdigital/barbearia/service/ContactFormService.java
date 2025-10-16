package com.wmdigital.barbearia.service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wmdigital.barbearia.dao.ContactFormRepository;
import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.entity.ContactForm;
import com.wmdigital.barbearia.framework.AbstractEntityService;

@Service
public class ContactFormService extends AbstractEntityService<ContactForm> {

	private final ContactFormRepository contactFormDAO;
	private final EmailService emailService;
	
	protected ContactFormService(ContactFormRepository contactFormDAO, EmailService emailService) {
		super(ContactForm.class, "contactForm");
		
		this.contactFormDAO = contactFormDAO;
		this.emailService = emailService;
		
	}

	@Override
	protected JpaRepository<ContactForm, UUID> getDao() {
		
		return contactFormDAO;
	}

	@Override
	protected void validateSave(ContactForm entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(ContactForm entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(ContactForm entity) {
		
		return entity.getId().toString();
	}

	@Override
	public List<ContactForm> findAllNew() {
		
		return getDao().findAll();
	}
	
	@Transactional
	public void mailConfirmation(ContactForm contactForm) {
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("nome", contactForm.getNome());
		model.put("email", contactForm.getEmail());
		model.put("mensagem", contactForm.getMensagem());
		
		emailService.sendTemplate(contactForm.getEmail(), "Confirmação de recebimento de mensagem", "emails/confirmacao-mensagem", model);
	
}

}
