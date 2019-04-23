package com.excilys.cdb.model;

public class ModelComputerShort {
	private int id;
	private String name;
	
	public ModelComputerShort(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return ("[id = " + id + ", nom de la machine = " + name + "]");
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
