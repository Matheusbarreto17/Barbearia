package com.wmdigital.barbearia.dao;

import java.time.LocalDateTime;
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

	@Query("""
		    SELECT a
		    FROM Agendamento a
		    WHERE a.barbeiro.id = :barbeiroId
		      AND a.inicio < :end
		      AND a.fim > :start
		    ORDER BY a.inicio ASC
		""")
	List<Agendamento> findAllByBarberAndOverlap(@Param("barbeiroId") UUID barbeiroId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
