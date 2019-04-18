package com.excilys.cdb.model;

import java.sql.Timestamp;

public class ModelComputer extends Model {
	private int id;
	private String name;
	private Timestamp di;
	private Timestamp dd;
	private String manufacturer;
	
	public ModelComputer(int id, String name, Timestamp di, Timestamp dd, String manufacturer) {
		this.setId(id);
		this.setName(name);
		this.setDi(di);
		this.setDd(dd);
		this.setManufacturer(manufacturer);
	}
	
	@Override
	public String toString() {
		return ("[id = " + id + ", nom de la machine = " + name + ", di = " + di
				+ ", dd = " + dd + ", nom de l'entreprise = " + manufacturer + "]");
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

	public String getManufacturer() {
		return manufacturer;
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

	public void setDd(Timestamp dd) {
		this.dd = dd;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
