package com.wmdigital.barbearia.dao;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


import com.wmdigital.barbearia.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
	
}
