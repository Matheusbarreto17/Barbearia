package com.wmdigital.barbearia.entity;

import java.util.UUID;

import com.wmdigital.barbearia.framework.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role")
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class Role extends BaseEntity{
	
	@Column(name = "name", nullable = false, unique = true, length = 50)
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
