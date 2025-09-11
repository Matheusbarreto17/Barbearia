package com.wmdigital.barbearia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wmdigital.barbearia.dao.RoleRepository;
import com.wmdigital.barbearia.entity.Role;
import com.wmdigital.barbearia.framework.AbstractEntityService;


@Service	
public class RoleService extends AbstractEntityService<Role>{
	
	
	private final RoleRepository roleDAO;

	protected RoleService(RoleRepository roleDAO) {
		super(Role.class, "role");
	
        this.roleDAO = roleDAO;

	}

	@Override
	protected JpaRepository<Role, UUID> getDao() {
		return roleDAO;
	}

	@Override
	protected void validateSave(Role entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateEdit(Role entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getIdEntity(Role entity) {

		return entity.getId().toString();
	}
	

	@Override
	public List<Role> findAllNew() {
		return roleDAO.findAll();
	}

	public Role findByName(String string) {
		return roleDAO.findByName(string).get();
	}

}
