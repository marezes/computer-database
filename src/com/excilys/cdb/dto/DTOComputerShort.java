package com.excilys.cdb.dto;

public class DTOComputerShort {
	private String id; // un id
	private String name; // Nom de l'entreprise
	
	public DTOComputerShort() {
		this.setId(null);
		this.setName(null);
	}
	
	public DTOComputerShort(String id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return ("[id = " + id + ", nom de la machine = " + name + "]");
	}

	// Getters
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	// Setters
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
