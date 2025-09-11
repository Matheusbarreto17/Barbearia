package com.wmdigital.barbearia.entity;

import com.wmdigital.barbearia.framework.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder	
public class Usuario extends BaseEntity{
	
	@Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    //como um usuario tem uma role, e esta role esta ligada a varios usuarios, usar esse mapeamento
    // como usuario tem o joincollumn ele é o dono da relação, mudaças de associação são persistidas nessa entidade.
  	
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "CHAR(36)")
    private Role role;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
    

    
    


}
	
