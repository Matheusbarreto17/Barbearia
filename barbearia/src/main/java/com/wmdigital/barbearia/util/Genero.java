package com.wmdigital.barbearia.util;

public enum Genero {
	
	    MASCULINO("Masculino"),
	    FEMININO("Feminino"),
	    NAO_BINARIO("Não binário"),
	    OUTRO("Outro"),
	    PREFIRO_NAO_INFORMAR("Prefiro não informar");

	    private final String rotulo;
	    
	    Genero(String rotulo) { this.rotulo = rotulo; }
	    public String getRotulo() { return rotulo; }
	}


