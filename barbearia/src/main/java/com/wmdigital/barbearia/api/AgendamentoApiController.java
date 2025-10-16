package com.wmdigital.barbearia.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wmdigital.barbearia.service.WizardAgenamentosService;

@RestController
@RequestMapping("/agendamentos/api")
public class AgendamentoApiController {

	private final WizardAgenamentosService wizard;

	public AgendamentoApiController(WizardAgenamentosService wizard) {
		
		this.wizard = wizard;
	}
	
	@GetMapping("/servicos")
	public List<Map<String, Object>> servicos() {
		
		System.out.println("passou no agendamento servico" + wizard.listarServicosMin().toString());
		
		
		return wizard.listarServicosMin();
	}
	
	@GetMapping("/barbeiros")
	public List<Map<String, Object>> barbeiros(UUID servicoId){
		
		
		return wizard.listarBarbeiroMin(servicoId);
	}
	
	@GetMapping("/slots")
	public List<Map<String, Object>> slots(UUID barbeiroId, UUID servicoID, LocalDate day){
		
		return wizard.listarSlotsOn(barbeiroId, servicoID, day);
	}
}
