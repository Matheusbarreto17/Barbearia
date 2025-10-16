package com.wmdigital.barbearia.form;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AgendamentoForm {

	@NotNull
	private UUID servicoId;
	
	@NotNull
	private UUID barbeiroId;
	
	@NotBlank
	private String inicio;
	
	@NotBlank
	private String clienteNome;
	
	@Email
	@NotBlank
	private String clienteEmail;
	
	@NotBlank
	private String clienteTelefone;
	
	
	public UUID getServicoId() {
		return servicoId;
	}
	public void setServicoId(UUID servicoId) {
		this.servicoId = servicoId;
	}
	public UUID getBarbeiroId() {
		return barbeiroId;
	}
	public void setBarbeiroId(UUID barbeiroId) {
		this.barbeiroId = barbeiroId;
	}
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getClienteNome() {
		return clienteNome;
	}
	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}
	public String getClienteEmail() {
		return clienteEmail;
	}
	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}
	public String getClienteTelefone() {
		return clienteTelefone;
	}
	public void setClienteTelefone(String clienteTelefone) {
		this.clienteTelefone = clienteTelefone;
	}
	
	
	
}
