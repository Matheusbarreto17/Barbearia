package com.wmdigital.barbearia.controller;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wmdigital.barbearia.dao.ClienteRepository;
import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Cliente;
import com.wmdigital.barbearia.entity.Role;
import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.entity.Usuario;
import com.wmdigital.barbearia.form.AgendamentoForm;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.AgendamentoService;
import com.wmdigital.barbearia.service.BarbeiroService;
import com.wmdigital.barbearia.service.ClienteService;
import com.wmdigital.barbearia.service.EmailService;
import com.wmdigital.barbearia.service.RoleService;
import com.wmdigital.barbearia.service.ServicoService;
import com.wmdigital.barbearia.service.UsuarioService;
import com.wmdigital.barbearia.util.Status;

import lombok.ToString;

@ToString
@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController extends AbstractController<Agendamento> {
	
	private final AgendamentoService agendamentoService;
	private final ServicoService servicoService;
	private final ClienteService clienteService;
	private final BarbeiroService barbeiroService;
	private final RoleService roleService;
	private final UsuarioService usuarioService;
	private final EmailService emailService;

	protected AgendamentoController(AgendamentoService agendamentoService, ServicoService servicoService, ClienteService clienteService, BarbeiroService barbeiroService, RoleService roleService, UsuarioService usuarioService,  EmailService emailService) {
		super("agendamento", Agendamento :: new);
		
		this.agendamentoService = agendamentoService;
		this.servicoService = servicoService;
		this.clienteService = clienteService;
		this.barbeiroService = barbeiroService;
		this.roleService = roleService;
		this.usuarioService = usuarioService;
		this.emailService = emailService;
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
	
    @PostMapping("/cancelar/{id}")
    public ModelAndView cancelarAgendamento(@PathVariable UUID id, RedirectAttributes ra ) {
    	
    	var	agendamento = agendamentoService.findOne(id);
    	
    	if (agendamento.getStatus().equals(Status.PENDENTE) || agendamento.getStatus().equals(Status.CONFIRMADO)) {
    	
    	agendamento.setStatus(Status.CANCELADO);
    	agendamentoService.save(agendamento);
    	
    	}
    	
    	return  new ModelAndView("redirect:/dashboard");
    	
    }
	
    @PostMapping("/concluir/{id}")
    public ModelAndView concluirAgendamento(@PathVariable UUID id, RedirectAttributes ra ) {
    	
    	var	agendamento = agendamentoService.findOne(id);
    	
    	if (agendamento.getStatus().equals(Status.CONFIRMADO)) {
    		
    		agendamento.setStatus(Status.CONCLUIDO);
    		agendamentoService.save(agendamento);
    	}
    	
    	return  new ModelAndView("redirect:/dashboard");
    	
    }
    
    @PostMapping("/confimar/{id}")
    public ModelAndView confimarAgendamento(@PathVariable UUID id, RedirectAttributes ra ) {
    	
    	var	agendamento = agendamentoService.findOne(id);
    	
    	if (agendamento.getStatus().equals(Status.PENDENTE)) {
    		
    		agendamento.setStatus(Status.CONFIRMADO);
    		agendamentoService.save(agendamento);
    	}
    	
    	return  new ModelAndView("redirect:/dashboard");
    	
    }
    
    @GetMapping({"/agendar"})
    public ModelAndView agendar() {
    	
    	   	 
   	    ModelAndView mav = new ModelAndView("private/agendamento/agendar");

   	  
        return mav;
    }
    
    @PostMapping("/agendar")
    public ModelAndView agendar(@Valid AgendamentoForm form, BindingResult br, RedirectAttributes ra, Model model) {
    	
    	if (br.hasErrors()) {
    		
			ra.addFlashAttribute("erro", "Dados invalidos no formulario");
			
			return new ModelAndView("private/agendamento/agendar");
		}
    	
    	UUID idServico = form.getServicoId();
    	UUID idBarbeiro = form.getBarbeiroId();
    	
    	Barbeiro barbeiro = barbeiroService.findOne(idBarbeiro);
    	Servico servico = servicoService.findOne(idServico);
    	
    	if (barbeiro == null || servico == null) {
    		
    		ra.addFlashAttribute("erro", "Barbeiro ou Servico null");
    		
    		return new ModelAndView("private/agendamento/agendar");
		}
    	   	 
    		LocalDateTime inicio = LocalDateTime.parse(form.getInicio());
    		LocalDateTime fim = inicio.plusMinutes(servico.getDuracao());
    	
    	Cliente cliente = clienteService.findByNome(form.getClienteNome());
    	
    		if (cliente == null) {
        		
    			cliente = new Cliente();
    			
        		String nome = form.getClienteNome();
        		String email = form.getClienteEmail();
        		String telefone = form.getClienteTelefone();
        		
        		cliente.setNome(nome);
        		cliente.setEmail(email);
        		cliente.setTelefone(telefone);
        		
        		Role role = roleService.findByName("ROLE_CLIENT");
        		
        		if (role == null) {
        			
        			ra.addFlashAttribute("erro", "Role null");
            		return new ModelAndView("private/agendamento/agendar");
				}
        		
        		Usuario usuario = new Usuario();
        		usuario.setUsername(nome);
        		usuario.setPassword("123");
        		usuario.setRole(role);
        		usuarioService.save(usuario);
        		cliente.setUsuario(usuario);
        		clienteService.save(cliente);		
        		
			}
    	
    	Agendamento agendamento = new Agendamento();
    		agendamento.setServico(servico);
    		agendamento.setBarbeiro(barbeiro);
    		agendamento.setCliente(cliente);
    		agendamento.setAtivo(true);
    		agendamento.setInicio(inicio);
    		agendamento.setFim(fim);
    		agendamento.setStatus(Status.PENDENTE);
    		
    		agendamentoService.save(agendamento);
    		agendamentoService.mailConfirmation(agendamento);
    		  		
    ModelAndView mav = new ModelAndView("private/agendamento/sucesso");	
    mav.addObject("agendamento", agendamento);
    
        	return mav;
    }
    
}
