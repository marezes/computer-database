package com.excilys.cdb.model;

public class ModelCompany {
	private int id;
	private String name;
	
	public ModelCompany(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return ("[id = " + id + ", nom de l'entreprise = " + name + "]");
	}
	
	// Getters
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
