package com.wmdigital.barbearia.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wmdigital.barbearia.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
	
	@Query("SELECT a FROM Agendamento a " + 
	       " WHERE a.cliente.usuario.id = :usuarioId " +
	         "OR a.barbeiro.usuario.id = :usuarioId")
	List<Agendamento> findByUsuarioId(@Param("usuarioId") UUID usuarioId);

}
