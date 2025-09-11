package com.wmdigital.barbearia.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.wmdigital.barbearia.entity.Cliente;
import com.wmdigital.barbearia.entity.Usuario;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.ClienteService;
import com.wmdigital.barbearia.service.UsuarioService;


@Controller
@RequestMapping("/clientes")
public class ClienteController extends AbstractController<Cliente> {
	
	private final ClienteService clienteService;
	private final UsuarioService usuarioService;

	protected ClienteController(ClienteService clienteService, UsuarioService usuarioService) {
		super("cliente", Cliente :: new);
		
		this.clienteService = clienteService;
		this.usuarioService = usuarioService;
		
	}

	@Override
	protected Cliente createEmptyEntity() {
		
		Cliente cliente = new Cliente();
		cliente.setAtivo(true);
		cliente.setUsuario(new Usuario());
		
		return cliente;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		
		return super.listEntitys();
	}

	@Override
	protected AbstractEntityService<Cliente> getService() {
	
		return clienteService;
	}
	
	@Override
	protected void preloadFormData(ModelMap modelMap) {
		
		modelMap.addAttribute("usuarios", usuarioService.findAll());
		
	}

}
