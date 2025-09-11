package com.wmdigital.barbearia.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmdigital.barbearia.entity.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
	
	Optional<Cliente> findByNome(String nome);

}
 