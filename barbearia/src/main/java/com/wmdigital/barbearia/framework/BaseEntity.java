package com.wmdigital.barbearia.framework;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@MappedSuperclass	
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	    
	  @Id
	  @GeneratedValue
	  @UuidGenerator // deixa o Hibernate gerar UUID
	  @JdbcTypeCode(SqlTypes.VARCHAR)         // mapeia para coluna texto
	  @Column(name = "id", length = 36, nullable = false, updatable = false)	
	protected UUID id;
	
	@Column(name = "isativo")
	protected boolean ativo =true;
	
    @CreatedDate
    @Column(name = "created_at",nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false,updatable = true)
    protected LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by",nullable = false, updatable = false)
    protected String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by",nullable = false, updatable = true)
    protected String updatedBy;	
	
    
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@JsonIgnore
	public boolean isNew() {
	    return (this.id == null);
	}

	public UUID getId() {
		return id;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}	
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	 public boolean isEnabled() {
	        return this.isAtivo(); // do BaseEntity
	    }

}
