package com.excilys.cdb.dto;

public class DTOComputer {
	private String id; // un id
	private String name; // Nom de l'ordinateur
	private String di; // date introduced
	private String dd; // date discontinued
	private String manufacturer; // le fabricant

	public DTOComputer() {
		this.id = null;
		this.name = null;
		this.di = null;
		this.dd = null;
		this.manufacturer = null;
	}
	
	public DTOComputer(String id, String name, String di, String dd, String manufacturer) {
		this.setId(id);
		this.setName(name);
		this.setDi(di);
		this.setDd(dd);
		this.setManufacturer(manufacturer);
	}

	// Getters
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDi() {
		return di;
	}

	public String getDd() {
		return dd;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	// Setters
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDi(String di) {
		this.di = di;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
