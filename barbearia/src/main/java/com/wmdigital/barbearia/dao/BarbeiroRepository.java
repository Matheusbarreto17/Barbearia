package com.wmdigital.barbearia.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmdigital.barbearia.entity.Barbeiro;


public interface BarbeiroRepository extends JpaRepository<Barbeiro, UUID> {
	
	Optional<Barbeiro> findByNome(String nome);

}
