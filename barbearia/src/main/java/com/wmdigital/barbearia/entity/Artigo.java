package com.wmdigital.barbearia.entity;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import com.wmdigital.barbearia.framework.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Artigo extends BaseEntity {
	
	@NotBlank
	@Column(name ="name", nullable = false, length = 120)
	protected String name;
	
	
	@Column(name ="descricao", length = 800)
	protected String descricao;
	
	@NotBlank
	@DecimalMin("0.0")
	@Digits(integer = 16, fraction = 2)
	@Column(name ="preco", nullable = false, precision = 19, scale = 2)
	protected BigDecimal preco;
	
	
	@Column(name ="foto")
	protected String foto;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public BigDecimal getPreco() {
		return preco;
	}


	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
}
