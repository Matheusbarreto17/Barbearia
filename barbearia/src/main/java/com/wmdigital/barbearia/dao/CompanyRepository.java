package com.wmdigital.barbearia.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmdigital.barbearia.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
