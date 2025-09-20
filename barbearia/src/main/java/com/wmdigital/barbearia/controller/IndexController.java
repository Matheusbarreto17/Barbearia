package com.wmdigital.barbearia.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Agendamento;
import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Cliente;
import com.wmdigital.barbearia.entity.Produto;
import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.AgendamentoService;
import com.wmdigital.barbearia.service.AutService;
import com.wmdigital.barbearia.service.BarbeiroService;
import com.wmdigital.barbearia.service.ClienteService;
import com.wmdigital.barbearia.service.ProdutoService;
import com.wmdigital.barbearia.service.ServicoService;
import com.wmdigital.barbearia.util.Status;


@Controller
public class IndexController extends AbstractController<Object> {
	
	
	@Autowired
	private BarbeiroService barbeiroService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private  PasswordEncoder encoder;
	
	@Autowired
	private AutService autService;
	
	@Autowired
	private AgendamentoService agendamentoServico;
	

	protected IndexController() {
		super("home", Object::new);
		
	}
	
	
	
    @GetMapping({"/", "/index"})
    public ModelAndView index() {
    	
    	System.out.println(encoder.encode("123"));
    	   	 
   	    ModelAndView mav = new ModelAndView("index");
   	    
   	   List<Barbeiro> barbeiros = barbeiroService.findAll(); 
   	   mav.addObject("barbeiros", barbeiros);
   	   
   	   List<Servico> servicos = servicoService.findAll();
   	   mav.addObject("servicos", servicos);
   	   
   	   List<Produto> produtos = produtoService.findAll();
   	   mav.addObject("produtos", produtos);
   	   
        return mav;
    }
    
    
    @GetMapping({"/login"})
    public ModelAndView login() {
    	
    	   	 
   	    ModelAndView mav = new ModelAndView("login");
//   	    mav.addObject("modelos", modelos);
   	  
        return mav;
    }
    
    
    @GetMapping({"/dashboard"})
    public ModelAndView dashboard() {
    	
   	    ModelAndView mav = new ModelAndView("private/dashboard");
   	    
   	 List<Cliente> clientes = clienteService.findAll();
   	int quantidadeCliente = clientes.size();
    mav.addObject("quantidadeCliente", quantidadeCliente);
    
    List<Barbeiro> barbeiros = barbeiroService.findAll();
   	int quantidadeBarbeiro = barbeiros.size();
    mav.addObject("quantidadeBarbeiro", quantidadeBarbeiro);
    
    List<Produto> produtos = produtoService.findAll();
   	int quantidadeProduto = produtos.size();
    mav.addObject("quantidadeProduto", quantidadeProduto);
    
    List<Servico> servicos = servicoService.findAll();
   	int quantidadeServico = servicos.size();
    mav.addObject("quantidadeServico", quantidadeServico);
    
   var user = autService.getUsuarioLogadoOrNull();
   
	   	if(user == null) {
		   
		   return index();
		   
	   		} else { 
	   		
	   			var meusAgendamentos = agendamentoServico.getAgendamentosByUsuer(user);
	   			mav.addObject("meusAgendamentos", meusAgendamentos);
	   		 
	   		 	
	   		 	var agendamentosAll = agendamentoServico.findAll();
	   		 	mav.addObject("agendamentosAll", agendamentosAll);
	   		 
	   		 		int qtdpendentes = countStatusAgendamentos(meusAgendamentos, Status.PENDENTE);
	   		 		int qtdconcluidos = countStatusAgendamentos(meusAgendamentos, Status.CONCLUIDO);
	   		 		int qtdpendentesadmin = countStatusAgendamentos(agendamentosAll, Status.PENDENTE);
	   		 		int qtdconcluidosadmin = countStatusAgendamentos(agendamentosAll, Status.CONCLUIDO);
	   		 		int qtdtodayadmin = countStatusAgendamentosToday(agendamentosAll, Status.PENDENTE);
	   		 		int qtdtoday = countStatusAgendamentosToday(meusAgendamentos, Status.PENDENTE);
	   		 		
	   		 			mav.addObject("qtdpendentes", qtdpendentes);
	   		 			mav.addObject("qtdconcluidos", qtdconcluidos);
	   		 			mav.addObject("qtdpendentesadmin", qtdpendentesadmin);
	   		 			mav.addObject("qtdconcluidosadmin", qtdconcluidosadmin);
	   		 			mav.addObject("qtdtodayadmin", qtdtodayadmin);
	   		 			mav.addObject("qtdtoday", qtdtoday);
	   }
	    
        return mav;
    }
    

	@Override
	protected Object createEmptyEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractEntityService<Object> getService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int countStatusAgendamentos(List<Agendamento> agendamentos, Status status) {
		
	    int contador = 0;

	    for (Agendamento agendamento : agendamentos) {
	    	
	        if (agendamento.getStatus() == status) {
	            contador++;
	        }
	    }

	    return contador;
	}
	
	public int countStatusAgendamentosToday(List<Agendamento> agendamentos, Status status) {
		
		 int contador = 0;

		    LocalDate hoje = LocalDate.now();

		    for (Agendamento agendamento : agendamentos) {
		        if (agendamento.getInicio() != null &&
		            agendamento.getInicio().toLocalDate().isEqual(hoje)) {

		            contador++;
		        }
		    }

		    return contador;
		}
}

