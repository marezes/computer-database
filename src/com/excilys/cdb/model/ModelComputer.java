package com.excilys.cdb.model;

import java.sql.Timestamp;

public class ModelComputer {
	private int id;
	private String name;
	private Timestamp di;
	private Timestamp dd;
	private String manufacturer;
	
	public ModelComputer(int id, String name, Timestamp di, Timestamp dd, String manufacturer) {
		this.id = id;
		this.name = name;
		this.di = di;
		this.dd = dd;
		this.manufacturer = manufacturer;
	}

	// Getters
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getDi() {
		return di;
	}

	public Timestamp getDd() {
		return dd;
	}

	// Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDi(Timestamp di) {
		this.di = di;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setDd(Timestamp dd) {
		this.dd = dd;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}
