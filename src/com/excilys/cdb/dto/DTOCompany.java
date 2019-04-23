package com.excilys.cdb.dto;

public class DTOCompany {
	private String id; 		// un id
	private String name; 	// Nom de l'entreprise
	
	public DTOCompany(String id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return ("[id = " + id + ", nom de l'entreprise = " + name + "]");
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
