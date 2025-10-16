package com.wmdigital.barbearia.entity;

import java.time.Instant;
import java.util.UUID;

import com.wmdigital.barbearia.framework.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "passwordreset")
public class PasswordReset extends BaseEntity {
	
	@ManyToOne(optional= false, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false, columnDefinition = "CHAR(36)")
	private Usuario usuario;
	
	@Column(nullable = false, unique = true, length = 64)
	private String token;
	
	@Column(nullable = false)
	private Instant expireAt;
	
	private boolean used = false;
	
	public static PasswordReset fill(Usuario usuario, long minutes) {
		
		PasswordReset passwordReset = new PasswordReset();
		passwordReset.usuario = usuario;
		passwordReset.token = UUID.randomUUID().toString().replace("-", "");
		passwordReset.expireAt = Instant.now().plusSeconds(minutes * 60);
		
		return passwordReset;
	}
	
	public boolean isValid() {
		
		return !used && Instant.now().isBefore(expireAt);
	}

	public Usuario getusuario() {
		
		return usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Instant expireAt) {
		this.expireAt = expireAt;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
}
