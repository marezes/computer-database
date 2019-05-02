package com.excilys.cdb.model;

import java.sql.Timestamp;

public class ModelComputer {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private ModelCompany modelCompany;
	
	public ModelComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, ModelCompany modelCompany) {
		this.setId(id);
		this.setName(name);
		this.setIntroduced(introduced);
		this.setDiscontinued(discontinued);
		this.modelCompany = modelCompany;
	}
	
	@Override
	public String toString() {
		return "ModelComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", modelCompany=" + modelCompany.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((modelCompany == null) ? 0 : modelCompany.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelComputer other = (ModelComputer) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (modelCompany == null) {
			if (other.modelCompany != null)
				return false;
		} else if (!modelCompany.equals(other.modelCompany))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// Getters
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public ModelCompany getModelCompany() {
		return modelCompany;
	}

	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public void setModelCompany(ModelCompany modelCompany) {
		this.modelCompany = modelCompany;
	}
}
