package com.wmdigital.barbearia.util;

public enum StorageDomain {
	BARBEIROS("barbeiros"),
    PRODUTOS("produtos"),
    SERVICOS("servicos"),
    CLIENTES("clientes");

    private final String folder;
    
    StorageDomain(String folder) { 
    	this.folder = folder;
    	}
    public String folder() {
    	return folder;
    	}

}
