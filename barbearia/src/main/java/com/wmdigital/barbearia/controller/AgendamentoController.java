package com.wmdigital.barbearia.controller;

import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.AgendamentoService;
import com.wmdigital.barbearia.service.BarbeiroService;
import com.wmdigital.barbearia.service.ClienteService;
import com.wmdigital.barbearia.service.ServicoService;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController extends AbstractController<Agendamento> {
	
	private final AgendamentoService agendamentoService;
	private final ServicoService servicoService;
	private final ClienteService clienteService;
	private final BarbeiroService barbeiroService;

	protected AgendamentoController(AgendamentoService agendamentoService, ServicoService servicoService, ClienteService clienteService, BarbeiroService barbeiroService) {
		super("agendamento", Agendamento :: new);
		
		this.agendamentoService = agendamentoService;
		this.servicoService = servicoService;
		this.clienteService = clienteService;
		this.barbeiroService = barbeiroService;
	}

	@Override
	protected Agendamento createEmptyEntity() {
		
		Agendamento agendamento = new Agendamento();
		agendamento.setAtivo(true);
		
		return agendamento;
	}
	
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		
		return super.listEntitys();
	}

	@Override
	protected AbstractEntityService<Agendamento> getService() {
		
		return agendamentoService;
	}
	
	@Override
	protected void preloadFormData(ModelMap modelMap) {
		
		modelMap.addAttribute("servicos", servicoService.findAll());
		modelMap.addAttribute("clientes", clienteService.findAll());
		modelMap.addAttribute("barbeiros", barbeiroService.findAll());
		
	}
	
}
