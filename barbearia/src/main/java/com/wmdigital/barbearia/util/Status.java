package com.wmdigital.barbearia.util;

public enum Status {
	
	PENDENTE("Pendente"),
	CONFIRMADO("Confirmado"),
	CONCLUIDO("Concluido"),
	CANCELADO("Cancelado");
	
    private final String status;
    
    
    Status(String status) {
    	
    	this.status = status;
		
	}

	public String getStatus() { 
    	return status; 
    	
    }

}
