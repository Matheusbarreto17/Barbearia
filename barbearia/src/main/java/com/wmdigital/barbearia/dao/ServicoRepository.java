package com.wmdigital.barbearia.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.wmdigital.barbearia.entity.Servico;

public interface ServicoRepository extends JpaRepository<Servico, UUID> {

}
