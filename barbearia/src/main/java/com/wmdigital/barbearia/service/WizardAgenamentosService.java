package com.wmdigital.barbearia.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Servico;

@Service
public class WizardAgenamentosService {

	private final ServicoService sevicoService;
	private final BarbeiroService barbeiroService;
	private final AgendamentoService agendamentoService;
	
	
	private WizardAgenamentosService(ServicoService sevicoService, BarbeiroService barbeiroService, AgendamentoService agendamentoService) {
		
		this.sevicoService = sevicoService;
		this.barbeiroService = barbeiroService;
		this.agendamentoService = agendamentoService;

	}
	
	public List<Map <String, Object>> listarServicosMin(){
		
		List<Servico> servicos = sevicoService.findAll();
		List<Map <String, Object>> resultado = new ArrayList<Map<String,Object>>(servicos.size());
		
		for (Servico servico : servicos) {
			
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", servico.getId());
			row.put("nome", servico.getName());
			row.put("duracaoMin", servico.getDuracao());
			resultado.add(row);
		}
		
		return resultado;
	}
	
	public List<Map <String, Object>> listarBarbeiroMin(UUID servicoId){
		
		List<Barbeiro> barbeiros = barbeiroService.findAll();
		List<Map <String, Object>> resultado = new ArrayList<Map<String,Object>>(barbeiros.size());
		
		for (Barbeiro  barbeiro : barbeiros) {
			
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", barbeiro.getId());
			row.put("nome", barbeiro.getNome());
			row.put("foto", barbeiro.getFoto());
			resultado.add(row);
			
		}
		
		return resultado;
	}
	
	public List<Map <String, Object>> listarSlotsOn(UUID barbeiroId, UUID servicoID, LocalDate day){
		
		Barbeiro barbeiro = barbeiroService.findOne(barbeiroId);	
//		Servico servico = sevicoService.findOne(servicoID);
		
//		int duracao = (servico != null && servico.getDuracao() > 0) ? servico.getDuracao() : 30;
		
		int duracao = 30;
		LocalDateTime start = day.atTime(LocalTime.of(9, 0));
		LocalDateTime end = day.atTime(LocalTime.of(18, 0));
		
		List<Agendamento> existente = agendamentoService.listAgendamentoDayBarber(barbeiroId, day);
		List<Map <String, Object>> out = new ArrayList<Map<String,Object>>();
		
		LocalDateTime t = start;
		
		while (!t.plusMinutes(duracao).isAfter(end)) {
		
			LocalDateTime slotStart = t;
			LocalDateTime slotEnd = t.plusMinutes(duracao);
			boolean conflito = false;
			
			for (Agendamento a  : existente) {
				
				if(slotStart.isBefore(a.getFim()) && slotEnd.isAfter(a.getInicio())) {
					
					conflito = true;
					
					break;
				}
			}
			if(!conflito) {
				
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("inicioIso", slotStart.toString());
				row.put("fimIso", slotEnd.toString());
				row.put("rotulo", String.format("%02d:%02d", slotStart.getHour(), slotStart.getMinute()));
				out.add(row);
			}
			
			t = t.plusMinutes(duracao);
		}
		
		
		return out;
	}
	
	
}
