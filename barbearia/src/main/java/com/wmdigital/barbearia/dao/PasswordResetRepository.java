package com.wmdigital.barbearia.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmdigital.barbearia.entity.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

	Optional<PasswordReset> findByToken(String token);
	
}
