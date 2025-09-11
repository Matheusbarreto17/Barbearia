package com.wmdigital.barbearia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Setter
@Getter
public class Produto extends Artigo {

}
