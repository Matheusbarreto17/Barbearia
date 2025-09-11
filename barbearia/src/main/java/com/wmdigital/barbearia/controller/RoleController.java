package com.wmdigital.barbearia.controller;

import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Role;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController extends AbstractController<Role> {

	private final RoleService roleservice;
	
	protected RoleController(RoleService roleservice) {
		super("role", Role ::new);
		
		this.roleservice= roleservice;
	}

	@Override
	protected Role createEmptyEntity() {
		
		Role role = new Role();
		role.setAtivo(true);
		
		return role;
	}

	@Override
	protected AbstractEntityService<Role> getService() {
		
		return roleservice;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {
		// TODO Auto-generated method stub
		return super.listEntitys();
	}

}
