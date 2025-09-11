package com.wmdigital.barbearia.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmdigital.barbearia.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

}
