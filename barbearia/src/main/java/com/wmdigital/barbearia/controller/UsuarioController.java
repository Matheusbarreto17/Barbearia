package com.wmdigital.barbearia.controller;

import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Role;
import com.wmdigital.barbearia.entity.Usuario;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.RoleService;
import com.wmdigital.barbearia.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController extends AbstractController<Usuario> {
	
	private final UsuarioService usuarioService;
	private final RoleService roleservice;

	protected UsuarioController(UsuarioService usuarioService, RoleService roleservice) {
		super("usuario", Usuario :: new);
		
		this.usuarioService = usuarioService;
		this.roleservice = roleservice;
	}

	@Override
	protected Usuario createEmptyEntity() {
		
		Usuario user = new Usuario();
		user.setAtivo(true);
		user.setRole(new Role());
		
		
		return  user;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		// TODO Auto-generated method stub
		return super.listEntitys();
	}

	@Override
	protected AbstractEntityService<Usuario> getService() {
		
		return usuarioService;
	}
	
	@Override
	protected void preloadFormData(ModelMap modelMap) {
		
		modelMap.addAttribute("roles", roleservice.findAll());
		
	}

}
