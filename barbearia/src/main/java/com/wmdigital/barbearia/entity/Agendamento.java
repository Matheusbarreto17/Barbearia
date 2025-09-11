package com.wmdigital.barbearia.entity;
import java.time.LocalDateTime;
import com.wmdigital.barbearia.framework.BaseEntity;
import com.wmdigital.barbearia.util.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamento")
public class Agendamento extends BaseEntity {
	
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "servico_id", nullable = false, columnDefinition = "CHAR(36)")
	private Servico servico;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "barbeiro_id", nullable = false, columnDefinition = "CHAR(36)")
	private Barbeiro barbeiro;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id", nullable = false, columnDefinition = "CHAR(36)")
	private Cliente cliente;
	
	@Column(name = "inicio")
	private LocalDateTime inicio;
	
	@Column(name = "fim")
	private LocalDateTime fim;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status = Status.PENDENTE;

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Barbeiro getBarbeiro() {
		return barbeiro;
	}

	public void setBarbeiro(Barbeiro barbeiro) {
		this.barbeiro = barbeiro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFim() {
		return fim;
	}

	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	

}
