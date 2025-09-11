package com.wmdigital.barbearia.entity;

import com.wmdigital.barbearia.framework.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "barbeiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Barbeiro extends BaseEntity {
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "foto")
	private String foto;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "especialidade")
	private String especialidade;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(name = "usuario_id", nullable = false, columnDefinition = "CHAR(36)")
	private Usuario usuario;

	public String getNome() {
		return nome;
	}
	

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getEspecialidade() {
		return especialidade;
	}


	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	

}
